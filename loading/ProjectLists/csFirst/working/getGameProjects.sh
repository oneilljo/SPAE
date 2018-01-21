#!/bin/bash

#Make sure to run these to completion and not just get a
#"Server Error" message. Try to run in off peak hours

#curl https://scratch.mit.edu/projects/70958100/remixes/ > Game1.html
#curl https://scratch.mit.edu/projects/20619111/remixes/ > Game2.html
curl https://scratch.mit.edu/projects/19646592/remixes/ > Game4.html

while [ "$(du -h Game4.html | grep -o "36K")" = "36K" ]; do
	echo ""
	echo "And again..."
	echo ""
	curl https://scratch.mit.edu/projects/19646592/remixes/ > Game4.html
	echo "$(du -h Game4.html | grep -o "36K")"
	echo ""
done

echo "GOT IT!!!"

#curl -I https://scratch.mit.edu/projects/20619111/remixes/ > Game2.html

#curl https://scratch.mit.edu/projects/72180668/remixes/ > Game3a.html
#curl https://scratch.mit.edu/projects/72180312/remixes/ > Game3b.html
#curl https://scratch.mit.edu/projects/71955350/remixes/ > Game3c.html
#curl https://scratch.mit.edu/projects/73517262/remixes/ > Game3d.html
#curl https://scratch.mit.edu/projects/19646592/remixes/ > Game4.html
#No Specified Starter Project for Game5.html
#curl https://scratch.mit.edu/projects/19411371/remixes/ > Game6.html
#curl https://scratch.mit.edu/projects/70949040/remixes/ > Game7a.html
#curl https://scratch.mit.edu/projects/70949258/remixes/ > Game7b.html
#curl https://scratch.mit.edu/projects/70947766/remixes/ > Game7c.html
#curl https://scratch.mit.edu/projects/21490386/remixes/ > Game8.html

#Print all Unique Projects to a .txt file
cat Game*.html | grep -o "/projects/[0-9]*" | grep -o "[0-9]*$" > Game.txt
awk '!seen[$0]++' Game.txt > new.txt
mv -f new.txt Game.txt
