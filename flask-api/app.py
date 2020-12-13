from flask import Flask, request
from redis import Redis, RedisError
from profanity_filter import ProfanityFilter

pf = ProfanityFilter()

app = Flask(__name__)

@app.route("/filter", methods=["POST"])
def filter():
    return pf.censor(request.json['text'])

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=80)