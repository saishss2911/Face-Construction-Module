import cv2
import dlib
import numpy as np
import os
import sys
from PIL import Image

orb = cv2.ORB_create()

best_similarity = 0
best_match_photo_path = ''+sys.argv[1]

# best_match_photo_path =r'C:\Users\91883\IdeaProjects\trial1\Scripts\Sketch 1.jpg'


best_match_photo = cv2.imread(best_match_photo_path)
similarity = 0

# Load the face detector and landmark predictor
detector = dlib.get_frontal_face_detector() # type: ignore
predictor = dlib.shape_predictor(os.path.join(os.path.dirname(__file__),"shape_predictor_68_face_landmarks.dat")) # type: ignore
# Load the two images

img_sketch = cv2.imread(best_match_photo_path)

# Convert the images to grayscale

gray_sketch = cv2.cvtColor(img_sketch, cv2.COLOR_BGR2GRAY)

# Detect faces in the images


faces_sketch = detector(gray_sketch)

# Extract facial landmarks from the faces

for face in faces_sketch:
    landmarks_sketch = predictor(gray_sketch, face)

# Convert the landmarks to numpy arrays

landmarks_sketch = np.array([(p.x, p.y) for p in landmarks_sketch.parts()])

kp2, des2 = orb.detectAndCompute(gray_sketch, None)

# Find the homography matrix between the two sets of landmarks


sketch_dir = r"C:\Users\91883\Desktop\face recog basic\Test_Sketches\sketch"

for images in os.listdir(r"C:\Users\91883\Desktop\face recog basic\Test_Sketches\sketch"):

    img_photo = cv2.imread(os.path.join(sketch_dir,images))

    gray_photo = cv2.cvtColor(img_photo, cv2.COLOR_BGR2GRAY)

    faces_photo = detector(gray_photo)

    for face in faces_photo:
        landmarks_photo = predictor(gray_photo, face)

    landmarks_photo = np.array([(p.x, p.y) for p in landmarks_photo.parts()])

    M, _ = cv2.findHomography(landmarks_sketch, landmarks_photo, cv2.RANSAC)

    kp1, des1 = orb.detectAndCompute(gray_photo, None)
    bf = cv2.BFMatcher(cv2.NORM_HAMMING, crossCheck=True)
    matches = bf.match(des1, des2)
    matches = sorted(matches, key=lambda x: x.distance)

    similarity = len(matches) / len(kp1) * 100

    if (best_similarity < similarity):
        best_similarity = similarity
        best_match_photo_path = os.path.join(sketch_dir,images)
        best_match_photo = cv2.imread(os.path.join(sketch_dir,images))


# Display the original photo, warped sketch, and similarity score


# best_match_photo = cv2.copyMakeBorder(best_match_photo,114,114,153,153,cv2.BORDER_CONSTANT,value=(255,255,255))
# best_match_photo = cv2.resize(best_match_photo,(720,810))
# result = np.concatenate((img_sketch,best_match_photo), axis=1)
# cv2.putText(result, f'Similarity: {similarity:.2f}%', (50, 50), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)
# cv2.imshow('Comparison', result)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
print(best_match_photo_path)
print(similarity)