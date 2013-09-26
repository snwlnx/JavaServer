import Image, ImageDraw
import json
import os

data_dir = "./data/"
img_dir = "./img/"
data_dir_list = os.listdir(data_dir);

for m in data_dir_list:	
	(name, ext) = m.split(".")
	if ext != "json":
		continue

	m = json.load(open(data_dir + m, "r"))
	im = Image.new("RGB", (m["width"], m["height"]), (255, 255, 255))

	draw = ImageDraw.Draw(im)
	for b in m["boxes"]:
		draw.rectangle(b, fill="black")

	im.save(img_dir + m["img"], "PNG")