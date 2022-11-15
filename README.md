# <span style="color: #ff4d4d"><b><span style="font-size: 26px">WirelessRedstone </span></b><span style="font-size: 15px">v0.4.1</span></span><br>
A simple redstone plugin that allows for wireless redstone signals to be sent over infinite distances and through worlds. This plugin is almost out of early development, any support, suggestions and help is appreciated. Join my discord at <a href="https://discord.gg/TsRTa7BUWm" target="_blank" class="externalLink" rel="nofollow">https://discord.gg/TsRTa7BUWm
</a><br>

<br>
<br>

<b><span style="font-size: 22px"><span style="color: #ff4d4d">#Showcase</span></span></b><br>
[![Showcase](https://img.youtube.com/vi/I6f77D2jJmU/0.jpg)](https://www.youtube.com/watch?v=I6f77D2jJmU)

<br>
<br>

<b><span style="font-size: 22px"><span style="color: #ff4d4d">Installation</span></span></b><br>
<ul>
<li>Drag &amp; drop the downloaded .jar file in your plugins folder and restart/reload your server.</li>
<li>Edit the configuration file to fit your demands.</li>
<li>Leave the database file untouched to avoid losing redstone devices when restarting/reloading the plugin.</li>
</ul>
<br>
<b><span style="font-size: 18px"><span style="color: #ff4d4d">
# How to use WirelessRedstone</span></span></b><br>

This concept is easy to use by anyone with any redstone expertise. Using this plugin will allow you to obtain 2 custom items. The Redstone Sender and the Redstone Receiver. You can edit the block type, item name and item lore in the configurations to fit your server.

Use **/device give <player> <device> <amount>**

You can replace _<player>_ with * so this command will apply to all online players. You can also replace _<device>_ with * to give both devices at once.

The _<device>_ is set to how ever you named the items in the configuration file. This is Redstone Sender and Redstone Receiver by default.

Upon placing down the devices anywhere in the world, you can right click either one to start the linking process. If you have clicked on a Redstone Sender, you will be asked to click any Redstone Receiver to establish a link between these two devices.

You can cancel a linking progress by using **/device link cancel**. A link will break if either devices break or get destroyed. You can have up to infinite links per device, depending on your settings.

In order to break a link without breaking a device, you can use the **/device link <breakall/breakfirst/breaklast>** command.
<br>
<br>

[![Tutorial](https://img.youtube.com/vi/O5CvW6SY454/0.jpg)](https://www.youtube.com/watch?v=O5CvW6SY454)

<br>
<br>
<b><span style="font-size: 18px"><span style="color: #ff4d4d">How do Redstone Devices work</span></span></b><br>
As the name suggests, the Redstone Sender, sends a redstone power signal towards any linked Redstone Receiver. The initial power signal is the highest redstone power the Redstone Sender is powered with.
This means that if you were to power a Redstone Sender with a redstone wire with a power of 8, that this value will be transmitted to any linked Redstone Receiver.

Without any delay, the Redstone Receiver, which received the power signal from its linked Redstone Sender, will emit its power to any block surrounding it. This will result in powering redstone wire, repeaters, lamps, pistons and more.

A Redstone Receiver can also power another Redstone Sender, allowing for more compact redstone machines.
<br>
<br>

<b><span style="font-size: 18px"><span style="color: #ff4d4d">How do you craft Redstone Devices</span></span></b><br>
In order to support survival gameplay, there are recipes for each device. However they are not custom but require a default recipe to craft. You can unlock the recipe when you unlock any redstone recipe.

**Crafting recipe for a Redstone Sender**<br>
![image](https://cdn.discordapp.com/attachments/972457153979875330/1040093579906928660/crafting-grid.png)

**Crafting recipe for a Redstone Receiver**<br>
![image](https://cdn.discordapp.com/attachments/972457153979875330/1040093541013143602/crafting-grid_1.png)

<br>
<b><span style="font-size: 18px"><span style="color: #ff4d4d">Permissions</span></span></b><br>
Permissions are by default disabled in the configuration. Once enabled, those with the correct permissions can overwrite certain configuration settings.
<br>
**Permission for everything:**<div style="padding-left: 30px"><i>
wirelessredstone.*
</i>&ZeroWidthSpace;</div>

**Permission for using commands:<br>**
<div style="padding-left: 30px"><i>
wirelessredstone.commands.*<br>
wirelessredstone.commands.wirelessredstone.*<br>
wirelessredstone.commands.wirelessredstone.reload<br>
wirelessredstone.commands.wirelessredstone.disable<br>
wirelessredstone.commands.device.*<br>
wirelessredstone.commands.device.give<br>
wirelessredstone.commands.device.info<br>
wirelessredstone.commands.device.link.cancel<br>
wirelessredstone.commands.device.link.breakall<br>
wirelessredstone.commands.device.link.breakfirst<br>
wirelessredstone.commands.device.link.breaklast<br>
</i>&ZeroWidthSpace;</div>

**Permission for using devices:<br>**
<div style="padding-left: 30px"><i>
_wirelessredstone.device.*<br>
wirelessredstone.device.place<br>
wirelessredstone.device.break<br>
wirelessredstone.device.noplacelimit<br>
wirelessredstone.device.nolinklimit<br>
</i>&ZeroWidthSpace;</div>

**Permission for linking devices:<br>**
<div style="padding-left: 30px"><i>
wirelessredstone.link.*<br>
wirelessredstone.link.create<br>
wirelessredstone.link.break<br>
wirelessredstone.link.nolimit<br>
wirelessredstone.link.infinitedistance<br>
wirelessredstone.link.crossworld<br>
</i>&ZeroWidthSpace;</div>

<b><span style="font-size: 18px"><span style="color: #ff4d4d">Configuration</span></span></b><br>
Modify your experience by changing the configurations to your need. You can find a more elaborated list of config settings in the config.yml file of the plugin.
A value of **0** represents infinity.
<br>
<div style="padding-left: 30px"><i>
SenderItemName: Redstone Sender<br>
SenderItemLore: Sends wireless Redstone signals<br>
ReceiverItemName: Redstone Sender<br>
ReceiverItemLore: Receives wireless Redstone signals<br>
SenderBlockType: red_terracotta<br>
ReceiverBlockType: green_terracotta<br>
Permissions: false<br>
Messages: true<br>
MaxDeviceInServer: 0<br>
MaxDevicesPerPlayer: 0<br>
MaxLinksInServer: 0<br>
MaxLinksPerPlayer: 0<br>
MaxLinksPerDevice: 0<br>
MaxLinkDistance: 0<br>
CrossWorldSignals: true<br>
ContactSignals: true<br>
SignalDelay: 0<br>
Overload: true<br>
OverloadTrigger: 8<br>
OverloadCooldown: 10<br>
</i>&ZeroWidthSpace;</div>
<br>

<b><span style="font-size: 18px"><span style="color: #ff4d4d">Languages Support</span></span></b><br>
Download preset messages.yml and config.yml files which are translate by contributors into other language at
https://www.spigotmc.org/resources/wirelessredstone.101871/

<br>
<b><span style="font-size: 18px"><span style="color: #ff4d4d">API Support</span></span></b><br>
The API can be accessed by anyone in anyway. This plugin supports a small number of API functions for other plugin developers to integrate and control WirelessRedstone.<br>
<br>
Access the API:<br>
<div style="padding-left: 30px"><i>
U_Api api = new U_Api();
</i>&ZeroWidthSpace;</div>
<br>
<br>
<i>Example of API usage</i><br>

![image](https://i.ibb.co/ySgbTN1/api.png)

<br>
<br>

<b><span style="font-size: 18px"><span style="color: #ff4d4d">Community Support</span></span></b><br>
If you have any further questions don't forget to contact me through discord or spigot.
Satisfied? Please leave a review!

Check out my Discord for suggestions, tips, questions and support.
https://discord.gg/TsRTa7BUWm

