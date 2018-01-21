#!/bin/bash

#Make sure to run these to completion and not just get a
#"Server Error" message. Try to run in off peak hours

#The first project was a simple introduction
curl https://scratch.mit.edu/projects/18820610/remixes/ > Music2a.html
#curl https://scratch.mit.edu/projects/101794558/remixes/ > Music2b.html
#curl https://scratch.mit.edu/projects/101795005/remixes/ > Music2c.html
#curl https://scratch.mit.edu/projects/101795335/remixes/ > Music2d.html
curl https://scratch.mit.edu/projects/19176469/remixes/ > Music3.html

#Projects 4-6 & 8 are not remixed. They are created individually.

#curl https://scratch.mit.edu/projects/19353852/remixes/ > Music7.html

#Print all Unique Projects to a .txt file
cat Music*.html | grep -o "/projects/[0-9]*" | grep -o "[0-9]*$" > Music.txt
awk '!seen[$0]++' Music.txt > new.txt
mv -f new.txt Music.txt
