from flask import Flask,request,jsonify
import numpy as np
import pickle

model = pickle.load(open('Furniture.pkl','rb'))

app = Flask(__name__)



@app.route('/FurnitureCondition',methods=['POST'])
def predict():
    ImageFile = request.form.get('ImageFile')
 

    input_query = np.array([[ImageFile]])

    result = model.predict(input_query)[0]

    return jsonify({'Image Rsult ':str(result)})

if __name__ == '__main__':
    app.run(debug=True)