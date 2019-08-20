# FXGetMAC - A tool for collection the MAC Adress of a computer

## Information

Was created to collect the MAC Adress of a couple of Laptops at my work.
Instead of starting the command shell and copy the Adress, or getting the
LOG File from Dhcp Server, i'm using this tool.

## How to work with it
All Configuration is made in config.xml.
You need an IP Adress to determine the active eth.

### The Format of config.xml
Important is the *pingIP*. The program tries to ping that IP, and can determin the
active eth and it's MAC adress.
The mysql/cmd ist saved to an Textfile (i need it for my database), where *$_1* is replaced with the *hostname*, and *$_2* is replaced with the *MAC adress*.
```
<config>
    <common>
        <pingIP>10.0.10.4/16</pingIP>
        <last>hostname</last>
    </common>
    <mysql>
        <cmd>INSERT INTO mac VALUES (null,$_1,$_2,-1);</cmd>
    </mysql>
</config>
```

## Finally
This software can be improved. Right now its working.
So it is provided as it is. *Enjoy*
