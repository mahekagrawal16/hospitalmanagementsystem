import cv2

# Load face detection model
face_cap = cv2.CascadeClassifier(cv2.data.haarcascades + "haarcascade_frontalface_default.xml")

# Start video capture from webcam
video_cap = cv2.VideoCapture(0)

while True:
    ret, video_data = video_cap.read()
    if not ret:
        break

    col = cv2.cvtColor(video_data, cv2.COLOR_BGR2GRAY)
    
    faces = face_cap.detectMultiScale(
        col,
        scaleFactor=1.1,
        minNeighbors=5,
        minSize=(30, 30),
        flags=cv2.CASCADE_SCALE_IMAGE
    )

    for (x, y, w, h) in faces:
        cv2.rectangle(video_data, (x, y), (x + w, y + h), (0, 255, 0), 2)

    cv2.imshow("video_live", video_data)

    if cv2.waitKey(10) == ord("a"):
        break

video_cap.release()
cv2.destroyAllWindows()
