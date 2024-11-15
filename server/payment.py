from flask import Flask

app = Flask(__name__)

@app.route("/pay", methods=["POST"])
def payment():
    print("You're paying...")
    return "Payment Successful"


@app.route("/refund")
def refund():
    print("We are refunding...")
    return "Refund successful"

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8080)