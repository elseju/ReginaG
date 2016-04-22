from config import *
from flask import Flask, request, redirect, render_template
from watson_developer_cloud import ToneAnalyzerV3Beta
from pymessenger.bot import Bot
from pymongo import MongoClient
import requests
import twilio.twiml
import json
import apiai
import uuid

app = Flask(__name__)

client = MongoClient()
db = client.reginag

@app.route("/")
def hello():
   return render_template('hello.html')

@app.route("/sms", methods=['GET', 'POST'])
def process_sms():
    """ Processes messages coming in via Twilio """
    phone_number = request.values.get('From', None)
    sms_message = request.values.get('Body', None)
    resp = twilio.twiml.Response()
    regina_answer = ask_regina(phone_number, sms_message)['text']
    resp.message(regina_answer)
    return str(resp)
    
@app.route("/fb", methods=['GET', 'POST'])
def process_fb():
    """ Processes messages coming in via Facebook Messenger """
    bot = Bot(FB_PAGE_ACCESS_TOKEN)
    
    # used by FB to verify webhook
    if request.method == 'GET':
        if (request.args.get("hub.verify_token") == FB_VERIFY_TOKEN):
                return request.args.get("hub.challenge")
                
    if request.method == 'POST':
        output = request.json
        event = output['entry'][0]['messaging']
        for x in event:
            if (x.get('message') and x['message'].get('text')):
                fb_message = x['message']['text']
                recipient_id = x['sender']['id']
                response = ask_regina(recipient_id, fb_message)
                text = response['text']
                intent = response['intent']
                regina_answer = text.encode('ascii', 'replace')
                bot.send_text_message(recipient_id, regina_answer)
            else:
                pass
    return regina_answer

def ask_regina(sender_id, message):
    #identify the current session
    session_id = find_session(sender_id)
    
    #query Api.ai
    ai = apiai.ApiAI(APIAI_CLIENT_ACCESS_TOKEN, APIAI_SUBSCRIPTION_KEY)
    ai_request = ai.text_request()
    ai_request.query = message
    ai_response = ai_request.getresponse()
    response_dict = json.loads(ai_response.read())
    regina_text = response_dict['result']['fulfillment']['speech']
    
    #check if an intent was identified by api.ai
    try:
        regina_intent = response_dict['result']['metadata']['intentName']
    except KeyError:
        regina_intent = "none"

    #if Bye intent, close session
    if regina_intent == "Bye":
        close_session(session_id)
        regina_text = "Thanks for playing!"
        
    #log message and response to db
    db.messages.insert_one({"sender_id": sender_id, "message": message, "response": regina_text})

    return {'text' : regina_text, 'intent' : regina_intent}

def find_session(sender_id):
    """ Checks if a session exists for the given sender id, else create a new one """
    session = db.sessions.find_one({'sender_id': sender_id})
    if session is None:    
        session_id = str(uuid.uuid4())
        db.sessions.insert_one({'sender_id': sender_id, 'session_id': session_id})
    else:
        session_id = session['session_id']
    return session_id
    
def close_session(session_id):
    """Deletes the session with the given id"""
    db.sessions.remove({'session_id': session_id})
    
def analyze_tone(conversation):
    """ Take conversation text and calculates the confidence score using Watson Tone Analyzer """
    tone_analyzer = ToneAnalyzerV3Beta(username=WATSON_USERNAME,password=WATSON_PASSWORD,version=WATSON_API_VERSION)
    tone_response = tone_analyzer.tone(conversation)
    confidence = tone_response['document_tone']['tone_categories'][1]['tones'][1]['score']
    return confidence

if __name__ == "__main__":
    app.run(host='0.0.0.0')
