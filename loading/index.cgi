#!/usr/bin/env perl

 
#-------------------------------------------------------------
# Program Name:	progress.cgi
# Author:	Joseph O'Neill
# Date:		June 2018
# Description:	A simple script to parse a user's SCRATCH 
#               project and display their work in an organized
#               and detail manner.
#-------------------------------------------------------------

use strict;
use warnings;

use CGI;
use Time::Local;
use HTML::TokeParser::Simple;

#-------------------------------------------------------------
# GLOBAL VARIABLES
#
  
my $cgi = CGI->new;

my $projectURL = $cgi->param("projectURL");
$projectURL =~ /^(?:(?:http(?:s)??:\/\/)?scratch\.mit\.edu\/projects\/)?([0-9]+)\/?(.+)?$/;

my $projectID = $1;
my $parser;

$projectURL = "https://scratch.mit.edu/projects/${projectID}/";
eval
{
  $parser = HTML::TokeParser::Simple->new(url => ${projectURL});
}
or do
{
  $parser = "Failed";
};

#print STDERR "Hmmmmm...$parser\n";

my $originalProjectID = "";
my $remixedProjectID = "";

if ($parser ne "Failed")
{
  while (my $anchor = $parser->get_tag('span'))
  {
    next unless defined(my $attr = $anchor->get_attr('class'));
    if ( $attr =~ /^attribute$/ )
    {
      $anchor = $parser->get_tag('a');
      my $href = $anchor->get_attr('href');
      if ( $href =~ /^\/projects\/(\d+)\/$/ )
      {
        $originalProjectID = $1;

        while ($anchor = $parser->get_tag('span'))
        {
          next unless defined(my $attr = $anchor->get_attr('class'));
          if ( $attr =~ /^attribute$/ )
          {
            $anchor = $parser->get_tag('a');
            my $href = $anchor->get_attr('href');
            if ( $href =~ /^\/projects\/(\d+)\/$/ )
            {
              $remixedProjectID = $originalProjectID;
              $originalProjectID = $1;

              last;
            }
          }
        }
        last;
      }
    }
  }
}

#while (my $anchor = $parser->get_tag('a'))
#{
#  next unless defined(my $href = $anchor->get_attr('href'));
#  if ( $href =~ /^\/projects\/(\d+)\/$/ )
#  {
#    $originalProjectID = $1;
#  }
#}

#my $originalProjectID = `curl -sS https://scratch.mit.edu/projects/${projectID}/ | grep -o '<a href=\"/projects/[[:digit:]]\{1,\}/\">' | tail -n 1 | grep -o '[[:digit:]]\{1,\}'`;

#
# END GLOBAL VARIABLES
#-------------------------------------------------------------
# MAIN
#

print_header();

# If the project is publicly shared
if ($parser ne "Failed") 
{ 
  print_success_body();
}
# Otherwise, print a failure message
else 
{ 
  print_failed_body(); 
}

print_footer();

# Don't try to download the project if it isn't publicly shared
if ($parser ne "Failed")
{
  download(${projectID});

  if ( $originalProjectID ne "" ) 
  {
    download(${originalProjectID});
  }

  if ( $remixedProjectID ne "" )
  {
    download(${remixedProjectID});
  }
}

exit(0);
#
# END MAIN

#-------------------------------------------------------------
sub print_header
# Begin printing printing the webpage. Feed the browser the
# http content-type. Open the html tag. Set the title of the 
# page. Declare the content-type. Load the header of the site
# as well as the favicon and background image.
#-------------------------------------------------------------
{
  # Head for HTML

  print CGI::header();

  print "<html>";
  print "  <head>";
  print "    <title>SCRATCH Thesis Project</title>";
  print "    <meta charset=\"UTF-8\">";
  print "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">";
  print "    <link rel=\"stylesheet\" href=\"../css/format.css\">";
  print "    <link rel=\"stylesheet\" href=\"../css/report.css\">";
  print "    <link rel=\"shortcut icon\" href=\"../images/favicon.ico\" type=\"image/x-icon\">";
  print "    <link rel=\"icon\" href=\"../images/favicon.ico\" type=\"image/x-icon\">";
  print "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>";
  print "  </head>";

  # Header to to page and background image.
  print "  <body onload=\"document.download.submit()\">";
  print "    <div class=\"display-container bgimg text-white\">";
  print "      <div class\"display-topright padding-small medium\">";
#  print "        <a href=\"../\">Home</a> | <a href=\"../about/\">About</a> | <a href=\"../login/\">Login</a> | <a href=\"../signup/\">Sign Up</a>";
  print "      </div>";
}
#-------------------------------------------------------------
sub print_success_body
# Print the first visible part of the page. Open the body tag.
# Print the page heading. If the user is an admin, state it.
#-------------------------------------------------------------
{
  print "    <div class=\"display-middle center\">";
  print "      <img class= \"animate-top\" src=\"../images/scratch.png\" alt=\"Title\">";
  print "      </br>";
  print "      <label class=\"animate-bottom padding-small\" for=\"download\">Downloading and processing your project...</label>";
  print "      </br>";
  print "      </br>";
  print "      <img class=\"animate-bottom\" style=\"width:50px;height:50px;\"  src=\"../images/loading.gif\" alt=\"Loading\">";
  print "      <form name=\"download\" method=\"post\" action=\"/scratch/progress/index.cgi\">";
  print "        <input type=\"hidden\" name=\"projectID\" id=\"projectID\" value=\"${projectID}\" />";
  print "      </form>";
  print "    </div>";
}
#-------------------------------------------------------------
sub print_failed_body
# Print the first visible part of the page. Open the body tag.
# Print the page heading. If the user is an admin, state it.
#-------------------------------------------------------------
{
  print "    <div class=\"display-middle center\">";
  print "      <img class= \"animate-top\" src=\"../images/scratch.png\" alt=\"Title\">";
  print "      <img class= \"animate-left\" src=\"../images/sad_cat.png\" alt=\"Sad Cat\" style=\"height:200; width:200;\">";
  print "      <h3 class= \"animate-right\">Uh oh! This project either isn't publicly shared or doesn't exist!</h3>";
  print "      <h4 class= \"animate-bottom\">If this is your project, click <a href=\"${projectURL}\" target=\"_blank\">here</a> and share the project.</h4>";
  print "      <h5 class= \"animate-bottom\">Once the project has been shared, <a href=\"../index.html\">go back</a> and try again.</h5>";
  print "    </div>";
}
#-------------------------------------------------------------
sub print_footer
# Prints the bottom portion of the webpage. Print a horizontal
# rule to signify the end of the page. Close the body tag and
# close the html tag.
#-------------------------------------------------------------
{
  print "      <div class=\"display-bottomleft padding-small tiny\">";
  print "      Created & Mantained by Joseph O'Neill. Website Repository can be found <a href=\"https://github.com/oneilljo/SCRATCH\" target=\"_blank\">here</a>.";
  print "      </div>";
  print "    </div>";
  print "  </body>";
  print "</html>";
}
#-------------------------------------------------------------
sub download
# Downloads the project on the system side, refreshing the 
# loaded page on completion.
#-------------------------------------------------------------
{
  my $downloadID = shift;

  system("./projectdownloader.py ${downloadID}");
  system("chmod 755 project-${downloadID}.sb2");
  system("chown apache:apache project-${downloadID}.sb2");
  system("cp project-${downloadID}.sb2 ../savedProjects");
  system("mv -f project-${downloadID}.sb2 ../SCATT/submissions/project-${downloadID}.sb2");
  system("/usr/bin/java -jar ../SCATT/Scatt.jar");
  system("rm -f ../SCATT/submissions/project-${downloadID}.sb2");
  system("rm -f zips/${downloadID}.zip");
  system("mv unzips/project-${downloadID}/project.json ../savedReports/project-${downloadID}");
  system("rm -rf unzips/${downloadID}");
}
#-------------------------------------------------------------
