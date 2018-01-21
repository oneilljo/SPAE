#!/bin/bash

#Make sure to run these to completion and not just get a
#"Server Error" message. Try to run in off peak hours

#The first project was a simple introduction
#curl https://scratch.mit.edu/projects/46413074/remixes/ > Sports1.html
#curl https://scratch.mit.edu/projects/46422268/remixes/ > Sports2a.html
#curl https://scratch.mit.edu/projects/46417186/remixes/ > Sports2b.html
#curl https://scratch.mit.edu/projects/46420978/remixes/ > Sports2c.html
#curl https://scratch.mit.edu/projects/46418628/remixes/ > Sports2d.html
curl https://scratch.mit.edu/projects/46475072/remixes/ > Sports3a.html
#curl https://scratch.mit.edu/projects/46480920/remixes/ > Sports3b.html
#curl https://scratch.mit.edu/projects/46481720/remixes/ > Sports3c.html
#curl https://scratch.mit.edu/projects/46824448/remixes/ > Sports4a.html
#curl https://scratch.mit.edu/projects/46826488/remixes/ > Sports4b.html
#curl https://scratch.mit.edu/projects/46821214/remixes/ > Sports4c.html
#curl https://scratch.mit.edu/projects/46828744/remixes/ > Sports4d.html
#curl https://scratch.mit.edu/projects/46428988/remixes/ > Sports5a.html
#curl https://scratch.mit.edu/projects/46429520/remixes/ > Sports5b.html
#curl https://scratch.mit.edu/projects/46431856/remixes/ > Sports5c.html
#curl https://scratch.mit.edu/projects/46433144/remixes/ > Sports5d.html
#curl https://scratch.mit.edu/projects/46436716/remixes/ > Sports6.html
#curl https://scratch.mit.edu/projects/46440826/remixes/ > Sports7.html
#curl https://scratch.mit.edu/projects/46570862/remixes/ > Sports8.html

#Print all Unique Projects to a .txt file
cat Sports*.html | grep -o "/projects/[0-9]*" | grep -o "[0-9]*$" > Sports.txt
awk '!seen[$0]++' Sports.txt > new.txt
mv -f new.txt Sports.txt
