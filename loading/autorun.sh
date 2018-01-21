#!/bin/bash

num=0
echo "$$" >> stats/pid.txt

cat $1 | while read -r line
do
    #Cleanup
    #rm -rf zips/*
    #rm -rf unzips/*
    #rm -rf ../savedProjects/*

    projectID="$line"
    ((num++))
    echo "$num: $projectID" >> stats/run.txt
    ./projectdownloader.py "$projectID" &> stats/waste.txt

    #sleep 5

    fileName="project_"$projectID".sb2"
    #echo "$fileName"

    chmod 777 $fileName 2>> stats/out.log
    #cp $fileName ../savedProjects 2>> stats/out.log
    mv -f $fileName ../SCATT/submissions/$fileName 2>> stats/out.log
    /usr/bin/java -jar ../SCATT/Scatt.jar #&> stats/waste.txt

    #sleep 1

    rm -rf ../SCATT/submissions/$fileName 2>> stats/out.log
    rm -rf zips/project_$projectID*
    rm -rf unzips/project_$projectID
    #rm -rf ../savedProjects/$filename*

done

echo "Complete!" >> stats/run.txt
