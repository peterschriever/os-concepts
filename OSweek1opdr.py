# using dpkg ofzo
# we moeten checken of lynx-cur installed is
# not installed:
#   check user privileges, if install permissions
#   install lynx-cur
# installed:
#   print("package is installed")

"""
Write a script that check whether a package is installed or not on a Debian based system.
Use the command line tool dpkg to execute this job.If the package is installed print a
message to the console that the package is already installed. If the package is not installed:
- check if the tool is started with root permission, if not, exit the script and tell the user that
root privileges are required to install the package
- install package
"""

# authorname : Femke Hoornveld & Peter Schriever
# version : 1.0
# date : 2016-11-24

import subprocess
import sys
# you can run the deb install via dpkg or by a dedicated module that handles .deb packages
# from apt.debfile import DebPackage
# variables i.e. packageName
# start a subporocess to execute a command

proc = subprocess.getoutput('dpkg -l | grep -w " lynx-cur "') # TODO: change to lynx-cur

# use the output from proc to determine whether the given package is installed or not
if proc == '':
    # not installed
    # check root (install permissions)
    isRoot = subprocess.getoutput('whoami') == 'root'
    if isRoot:
        # install package
        print("Installeren van lynx-cur package wordt gestart.")
        installStatus = subprocess.getstatusoutput('aptitude install lynx-cur')
        if installStatus[0] != 0:
            print("Package lynx-cur kon niet geïnstalleerd worden.")
            sys.exit()
        print("Installatie van lynx-cur was succesvol!")
        sys.exit()

    print("Could not install package. Make sure you have root permissions!")
    sys.exit()

# installed
print("Package lynx-cur is al geïnstalleerd.")
