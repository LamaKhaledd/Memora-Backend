from flask import Flask, request, jsonify
import joblib
import numpy as np

app = Flask(__name__)

log_model = joblib.load('logistic_model.pkl') 
vectorizer = joblib.load('vectorizer.pkl')  

@app.route('/predict', methods=['POST'])
def predict():
    print("Received request:", request.get_data())  
    
    data = request.get_json()
    print("Parsed JSON:", data) 
    
    sample_paragraph = data.get('text')
    print("Extracted 'text':", sample_paragraph) 
    
    if not sample_paragraph:
        print("Error: No 'text' provided in request.")  
        return jsonify({'error': 'No text provided'}), 400

    sample_tfidf = vectorizer.transform([sample_paragraph])
    print("TF-IDF vectorized input:", sample_tfidf.toarray())  
    
    predicted_category = log_model.predict(sample_tfidf)
    print("Predicted category:", predicted_category[0])  
    
    return jsonify({'predicted_category': predicted_category[0]})

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)  
