#!/bin/bash

#sar -u 5 2160 > stats/CPU-Usage.txt &
#sar -r 5 2160 > stats/Memory-Usage.txt &

num=0
echo "$$" >> stats/pid.txt
echo "Start Time: $(date -u)" >> stats/timing.txt

cat $1 | while read -r line
do
    #Cleanup
    #rm -rf zips/*
    #rm -rf unzips/*
    #rm -rf ../savedProjects/*

    projectID="$line"
    ((num++))
    echo "$num: $projectID -> $(date -u)" >> stats/run.txt
    ./projectdownloader.py "$projectID" &> stats/waste.txt

    #sleep 2

    fileName="project-${projectID}.sb2"
    #echo "$fileName"

    chmod 755 $fileName 2>> stats/out.log
    #cp $fileName ../savedProjects 2>> stats/out.log
    mv -f $fileName ../SCATT/submissions/$fileName 2>> stats/out.log
    /usr/bin/java -jar ../SCATT/Scatt.jar &> stats/waste.txt

    rm -rf ../SCATT/submissions/$fileName 2>> stats/out.log
    rm -f zips/project-$projectID*
    rm -rf unzips/project-$projectID
    rm -f ../savedProjects/$filename*

done

echo "Finish Time: $(date -u)" >> stats/timing.txt
echo "Complete!" >> stats/run.txt
