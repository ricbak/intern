from flask import Flask
import cv2
import time
import requests
from flask_cors import CORS
import copy

app = Flask(__name__)
CORS(app)

def learning(azureId):
    print("AzureId: {}".format(azureId))
    
    cv2.namedWindow("window", cv2.WINDOW_FULLSCREEN)

    cap = cv2.VideoCapture(0)

    # This script will detect faces via your webcam.
    # Tested with OpenCV3

    # Create the haar cascade
    faceCascade = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")

    savedFaces = 0
    detectedFaces = 1
    
    # After 10 faces are saved, return oke
    while(savedFaces < 2):

        # Capture frame-by-frame
        ret, frame = cap.read()

        # Our operations on the frame come here
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

       # Detect faces in the image
        faces = faceCascade.detectMultiScale(
            gray,
            scaleFactor=1.1,
            minNeighbors=5,
            minSize=(50, 50)
            #flags = cv2.CV_HAAR_SCALE_IMAGE
        )

        # print("Found {0} faces!".format(len(faces)))

        rectangleFrame = copy.deepcopy(frame)

        # Draw a rectangle around the faces
        for (x, y, w, h) in faces:

            cv2.rectangle(rectangleFrame, (x, y), (x+w, y+h), (0, 255, 0), 2)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            return rectangleFrame

        # Display the resulting frame
        cv2.imshow('window', rectangleFrame)

        # When a face is found, add the detectedFaces counter 
        if len(faces) > 0:
            # print("detectedFaces + 1")
            detectedFaces += 1

        print(detectedFaces)

        # After each 100 detectedFaces, do a request to the back-end
        if detectedFaces % 100 == 0:
            print("detected faces modulo 100")
        
            
            # Save image in directory so it can be send to back-end
            for (x, y, w, h) in faces:                
                crop_img = frame[(y-40):(y+40)+h, (x-10):(x+10)+w]
                cv2.imwrite("register-face.jpg", crop_img)

            # do a request to backend
 
            url = "http://localhost:8080/person/face/add"
            data = {'personId': azureId}
            files = {'file': open('register-face.jpg', 'rb')}
            response = requests.post(url, files=files, data=data)

            if response.ok :
                savedFaces += 1

            print("{}: request to back-end".format(savedFaces))


    # When everything done, release the capture
    print("releasing")
    cap.release()
    cv2.destroyAllWindows()

    return "Oke"


@app.route('/register/<id>', methods = ['GET'])
def faceRegister(id):
    learning(id)
    return "Uw gezicht is nu bekend bij ons, U kun de ruimte verlaten."


def identify():
    
    cv2.namedWindow("window", cv2.WINDOW_FULLSCREEN)
    cv2.moveWindow("window", 430, 315)
    cap = cv2.VideoCapture(0)

    # This script will detect faces via your webcam.
    # Tested with OpenCV3

    # Create the haar cascade
    faceCascade = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")

    tries = 0
    identified = False
    identifiedName = "'"
    detectedFaces = 1
    crop_img = None

    
    # After 10 faces are saved, return oke
    while(tries < 3 and identified == False):

        # Capture frame-by-frame
        ret, frame = cap.read()

        # Our operations on the frame come here
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

       # Detect faces in the image
        faces = faceCascade.detectMultiScale(
            gray,
            scaleFactor=1.1,
            minNeighbors=5,
            minSize=(50, 50)
            #flags = cv2.CV_HAAR_SCALE_IMAGE
        )

        # print("Found {0} faces!".format(len(faces)))

        rectangleFrame = copy.deepcopy(frame)
        # Draw a rectangle around the faces
        for (x, y, w, h) in faces:
            rectangleFrame = cv2.rectangle(rectangleFrame, (x, y), (x+w, y+h), (0, 255, 0), 2)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            return frame

        # Display the resulting frame
        cv2.imshow('window', rectangleFrame)

        # When a face is found, add the detectedFaces counter 
        if len(faces) > 0:
            print("detectedFaces: {} + 1".format(detectedFaces))
            detectedFaces += 1

        # After each 30 detectedFaces, do a request to the back-end
        if detectedFaces % 30 == 0:
            print("tried {} times".format(tries))
            tries += 1
            
            # Save image in directory so it can be send to back-end
            for (x, y, w, h) in faces:

                crop_img = frame[(y-40):(y+40)+h, (x-10):(x+10)+w]

                cv2.imwrite("identify-face.jpg", crop_img)

            # do a request to backend
            files = {'file': open('identify-face.jpg', 'rb')}
            url = "http://localhost:8080/person/detect"
            response = requests.post(url, files=files)

            print("reponse-status: {}".format(response.ok))
            if response.ok :
                print("content: {}".format(response.text))
                identified = True
                identifiedName = response.text
            # if good response, set identified name

            print("{}: request to back-end".format(tries))


    # When everything done, release the capture
    cap.release()
    cv2.destroyAllWindows()

    if identified :
        return "Welkom {}".format(identifiedName)

    else :
        return "We hebben u niet kunnen identificeren"

@app.route('/identify', methods = ['GET'])
def faceDetection():
    identified = identify()

    return identified

@app.route('/', methods = ['GET'])
def index():
   return ""

if __name__ == '__main__':
    app.run(port = 5000)

