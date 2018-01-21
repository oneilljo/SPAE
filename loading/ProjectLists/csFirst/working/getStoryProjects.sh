#!/bin/bash

#Make sure to run these to completion and not just get a
#"Server Error" message. Try to run in off peak hours

curl https://scratch.mit.edu/projects/82476890/remixes/ > Story1a.html
#curl https://scratch.mit.edu/projects/92342255/remixes/ > Story1b.html
#curl https://scratch.mit.edu/projects/92343207/remixes/ > Story1c.html
curl https://scratch.mit.edu/projects/24001065/remixes/ > Story3.html
#curl https://scratch.mit.edu/projects/24799712/remixes/ > Story4a.html
#curl https://scratch.mit.edu/projects/24799859/remixes/ > Story4b.html
#curl https://scratch.mit.edu/projects/24799659/remixes/ > Story4c.html
#curl https://scratch.mit.edu/projects/24799545/remixes/ > Story4d.html
#curl https://scratch.mit.edu/projects/91519983/remixes/ > Story5a.html
#curl https://scratch.mit.edu/projects/91521078/remixes/ > Story5b.html
#curl https://scratch.mit.edu/projects/91521482/remixes/ > Story5c.html
#curl https://scratch.mit.edu/projects/91522453/remixes/ > Story5d.html
#curl https://scratch.mit.edu/projects/91523025/remixes/ > Story5e.html
#curl https://scratch.mit.edu/projects/24738335/remixes/ > Story6.html
#curl https://scratch.mit.edu/projects/94026340/remixes/ > Story8.html

#Projects 2 and 7 were not produced using a starter project

#Print all Unique Projects to a .txt file
cat Story*.html | grep -o "/projects/[0-9]*" | grep -o "[0-9]*$" > Story.txt
awk '!seen[$0]++' Story.txt > new.txt
mv -f new.txt Story.txt
