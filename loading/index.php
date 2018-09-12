<!DOCTYPE html>
<html>
<title>SCRATCH Thesis Project</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/format.css">
<link rel="stylesheet" href="../css/font.css">
<link rel="stylesheet" href="../css/style.css">
<link rel="icon" href="../images/favicon.ico" type="image/x-icon">
<body onload="move();">
  <div class="bgimg w3-display-container w3-animate-opacity w3-text-white">
    <div class="w3-display-topright w3-padding-small w3-medium">
      <a href="../">Home</a> | <a href="../about/">About</a> | <a href="../login/">Login</a> | <a href="../signup/">Sign Up</a>
    </div>
    <div class="w3-display-middle w3-center">
      <img class= "w3-animate-top" src="../images/scratch.png" alt = "Title">
      <?php
        function download()
        {
            $projectID = preg_replace('/[^0-9]/',"",$_POST["project"]);

	    shell_exec("./projectdownloader.py " . $projectID);

	    shell_exec("sleep 1");
	    
	    shell_exec("chmod 777 project-" . $projectID . ".sb2");
	    shell_exec("chown apache:apache project-" . $projectID . ".sb2");
            shell_exec("cp project-" . $projectID . ".sb2 ../savedProjects");
            shell_exec("mv -f project-" . $projectID . ".sb2 ../SCATT/submissions/project-" . $projectID . ".sb2");
	    shell_exec("sleep 1");
            shell_exec("/usr/bin/java -jar ../SCATT/Scatt.jar");
	   
	    //Cleanup for disk space
	    shell_exec("rm -rf ../SCATT/submissions/" . $projectID . ".sb2");
	    shell_exec("rm -rf zips/" . $projectID . ".zip");
	    shell_exec("mv unzips/project-" . $projectID . "/project.json ../savedReports/project-" . $projectID);
	    shell_exec("rm -rf unzips/" . $projectID);
        }
        download();
      ?>
      </br>
      <div class="w3-light-grey w3-round-xlarge w3-animate-bottom">
        <div id="myBar" class="w3-container w3-blue w3-round-xlarge" style="height:24px;width:1%"></div>
      </div>
      <script>
        function move() {
	  var projectID = "<?php echo $_POST['project']?>";
          var elem = document.getElementById("myBar"); 
          var width = 1;
          var id = setInterval(frame, 30);
          function frame() {
            if (width >= 100) {
              clearInterval(id);
              window.location = "../progress/index.html?projectid=" + projectID;
            } else {
              width++; 
              elem.style.width = width + '%'; 
            }
          }
        }
        </script>
    </div>
    <div class="w3-display-bottomleft w3-padding-small w3-tiny">
    Created & Mantained by Joseph O'Neill. Website Repository can be found <a href="https://github.com/oneilljo/SCRATCH_Analysis" target="_blank">here</a>.
    </div>
  </div>
</body>
</html>
