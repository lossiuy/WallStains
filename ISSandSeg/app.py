import cv2
from flask import Flask, request, jsonify
from flask_cors import CORS
import predict

app = Flask(__name__)
CORS(app)  # 为整个应用启用 CORS


@app.route('/process_image', methods=['POST'])
def process_image():
    json_data = request.json
    if 'image_path' in json_data:
        image_path = json_data['image_path']
        print(image_path)
    else:
        return jsonify({"error": "No image file provided"}), 400
    results = predict.predict(image_path)
    image = cv2.imread(image_path)
    ress = predict.get_res(results, image, image_path)
    return jsonify({"res": ress}), 200


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8081)
