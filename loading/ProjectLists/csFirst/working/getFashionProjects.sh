#!/bin/bash

#Make sure to run these to completion and not just get a
#"Server Error" message. Try to run in off peak hours

curl https://scratch.mit.edu/projects/68196654/remixes/ > Fashion1.html
curl https://scratch.mit.edu/projects/69928910/remixes/ > Fashion2a.html
curl https://scratch.mit.edu/projects/69929124/remixes/ > Fashion2b.html
curl https://scratch.mit.edu/projects/69928094/remixes/ > Fashion2c.html
curl https://scratch.mit.edu/projects/67532122/remixes/ > Fashion3a.html
curl https://scratch.mit.edu/projects/28301792/remixes/ > Fashion3b.html
curl https://scratch.mit.edu/projects/67211854/remixes/ > Fashion3c.html
curl https://scratch.mit.edu/projects/67199596/remixes/ > Fashion3d.html
curl https://scratch.mit.edu/projects/68245046/remixes/ > Fashion4.html
curl https://scratch.mit.edu/projects/28302190/remixes/ > Fashion5.html
curl https://scratch.mit.edu/projects/70534922/remixes/ > Fashion6.html
curl https://scratch.mit.edu/projects/28272848/remixes/ > Fashion7.html
curl https://scratch.mit.edu/projects/28302898/remixes/ > Fashion8.html

#Print all Unique Projects to a .txt file
cat * | grep -o "/projects/[0-9]*" | grep -o "[0-9]*$" > Fashion.txt
awk '!seen[$0]++' Fashion.txt > new.txt
mv new.txt Fashion.txt
