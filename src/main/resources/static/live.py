#!/usr/bin/python
# This script will detect faces via your webcam.
# Tested with OpenCV3

import cv2

cap = cv2.VideoCapture(0)

# Create the haar cascade
faceCascade = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")

detectedFaces = 0
frameCount = 0

print("python script")

while(detectedFaces < 1):

	# Capture frame-by-frame
	ret, frame = cap.read()

	# Our operations on the frame come here
	gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

	if frameCount % 40 == 0:
		# Detect faces in the image
		faces = faceCascade.detectMultiScale(
			gray,
			scaleFactor=1.1,
			minNeighbors=5,
			minSize=(50, 50)
			#flags = cv2.CV_HAAR_SCALE_IMAGE
		)

	print("Found {0} faces!".format(len(faces)))

	# Draw a rectangle around the faces
	for (x, y, w, h) in faces:
		# cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)
		crop_img = frame[y:y+h, x:x+w]
		cv2.imwrite("cropped.jpg", crop_img)


	# time.sleep(1)
	frameCount += 1

	if cv2.waitKey(1) & 0xFF == ord('q'):
		break

	# Display the resulting frame
	cv2.imshow('frame', frame)

	if len(faces) > 0:
		detectedFaces += 1

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()
