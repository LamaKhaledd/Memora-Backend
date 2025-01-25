import joblib
import pandas as pd
import nltk
from sklearn.linear_model import LogisticRegression
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split

bbc_data = pd.read_csv('bbc-text-1.csv')

nltk.download('stopwords')
nltk.download('punkt')

vectorizer = TfidfVectorizer(
    stop_words='english',
    token_pattern=r'\b[a-zA-Z]+\b',
    max_df=0.85,
    min_df=0.05,
    max_features=300,
    ngram_range=(1, 2)
)

X = vectorizer.fit_transform(bbc_data['text'])
y = bbc_data['category']

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

log_model = LogisticRegression(
    multi_class='multinomial',
    solver='saga',
    penalty='l1',
    C=1.0,
    max_iter=1000
)

log_model.fit(X_train, y_train)

joblib.dump(log_model, 'logistic_model.pkl')
joblib.dump(vectorizer, 'vectorizer.pkl')

print("Model and vectorizer saved successfully!")
