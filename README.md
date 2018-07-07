# FXGetMAC - A tool for collection the MAC Adress of a computer

## Information

Was created to collect the MAC Adress of a couple of Laptops at my work.
Instead of starting the command shell and copy the Adress, or getting the
LOG File from Dhcp Server, i'm using this tool.

## How to work with it
1. All Configuration is made in config.xml.
   You need an IP Adress to determine the active eth

### The Format of config.xml
```
<config>
    <common>
        <pingIP>10.0.10.4/16</pingIP>
        <last>hostname</last>
    </common>
</config>
```

## Finally
This software can be improved. Right now its working.
So it is provided as it is. *Enjoy*
