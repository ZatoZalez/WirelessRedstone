# <span style="color: #ff4d4d"><b><span style="font-size: 26px">WirelessRedstone </span></b><span style="font-size: 15px">v0.3</span></span><br>
A simple redstone plugin that allows for wireless redstone signals to be sent over infinite distances and through worlds. This plugin is almost out of early development, any support, suggestions and help is appreciated. Join my discord at <a href="https://discord.gg/TsRTa7BUWm" target="_blank" class="externalLink" rel="nofollow">https://discord.gg/TsRTa7BUWm</a><br>
<br>
<br>

<b>Click to Watch</b><br>
[![Click to Watch](https://img.youtube.com/vi/testtttt/0.jpg)](https://www.youtube.com/watch?v=testtttt)

<br>
<br>
<b><span style="font-size: 22px"><span style="color: #ff4d4d">Installation</span></span></b><br>
<ul>
<li>Drag &amp; drop the downloaded .jar file in your plugins folder and restart/reload your server.</li>
<li>Edit the configuration file to fit your demands.</li>
<li>Leave the database file untouched to avoid losing redstone devices when restarting/reloading the plugin.</li>
</ul><br>
<b><span style="font-size: 18px"><span style="color: #ff4d4d">
How to use WirelessRedstone</span></span></b><br>

This concept is easy to use by anyone with any redstone expertise. Using this plugin will allow you to obtain 2 custom items. 
The <span style="color: #ff4d4d"><b>RedstoneSender</b></span> and the <span style="color: #ff4d4d"><b>RedstoneReceiver</b></span>. 
You can edit the block type in the configurations to fit your server.

<br>
Use <b>/givedevice &lt;player&gt; &lt;devicetype&gt; &lt;amount&gt;</b> to give yourself or others a 'redstonesender' or a 'redstonereceiver'.<br>
<br>
Upon placing down the devices anywhere in the world, you can <b>right </b>click either one to start the linking process. If you have clicked on a <span style="color: #ff4d4d"><b>RedstoneSender</b></span>, you will be asked to click any <span style="color: #ff4d4d"><b>RedstoneReceiver</b></span> to establish a link between these two devices.
<br>
You can cancel a linking progress by using <b>/cancellink</b>. A link will break if either devices break or get removed. You can have up to <b>infinitive</b> links per device.

<br>
<br>
<b><span style="font-size: 18px"><span style="color: #ff4d4d">How do Redstone Devices work</span></span></b><br>
As the name suggests, the <span style="color: #ff4d4d"><b>RedstoneSender</b></span>, sends a redstone power signal towards any linked <span style="color: #ff4d4d"><b>RedstoneReceiver</b></span>. The initial power signal is the highest redstone power the <span style="color: #ff4d4d"><b>RedstoneSender</b></span> is powered with.
<br>
This means that if you were to power a <span style="color: #ff4d4d"><b>RedstoneSender</b></span> with a redstone wire with a power of 8, that this value will be transmitted to any linked <span style="color: #ff4d4d"><b>RedstoneReceiver</b></span>.
<br>
<br>
Without any delay, the <span style="color: #ff4d4d"><b>RedstoneReceiver</b></span>, which received the power signal from its linked <span style="color: #ff4d4d"><b>RedstoneSender</b></span>, will emit its power to any block surrounding it. This will result in powering redstone wire, repeaters, lamps, pistons and more.
<br>
A <span style="color: #ff4d4d"><b>RedstoneReceiver</b></span> can also power another <span style="color: #ff4d4d"><b>RedstoneSender</b></span>, allowing for more compact redstone machines.
<br>
<br>
<br>
<br>

![image](image)

<br>
<br>
<br>
<br>
to be edited...<br>
<s><b><span style="font-size: 18px"><span style="color: #ff4d4d">Permissions</span></span></b><br>
Permissions are by default <b>disabled</b>. Please read the configuration of the plugin in order to enable them. The following permissions can be used.<br>
<br>
Permission to use said commands:<br>
<div style="padding-left: 30px"><i> wirelessredstone.commands.cancellink<br>
wirelessredstone.commands.givedevice.redstonesender<br>
wirelessredstone.commands.givedevice.redstonereceiver</i>&ZeroWidthSpace;</div><br>
<br>
Permission to place devices:<br>
<div style="padding-left: 30px"><i> wirelessredstone.place.redstonesender<br>
wirelessredstone.place.redstonereceiver</i>&ZeroWidthSpace;</div><br>
<br>
Permission to break devices or linked devices:<br>
<div style="padding-left: 30px"><i> wirelessredstone.break.redstonesender<br>
wirelessredstone.break.redstonereceiver<br>
wirelessredstone.break.link</i>&ZeroWidthSpace;</div><br>
<br>
Permission to link devices, crossworld or with no distance limit:<br>
<div style="padding-left: 30px"><i> wirelessredstone.redstonedevice.link<br>
wirelessredstone.redstonedevice.crossworld<br>
wirelessredstone.redstonedevice.infinitedistance</i>&ZeroWidthSpace;</div><br>
<br>
<b><span style="font-size: 18px"><span style="color: #ff4d4d">Configuration</span></span></b><br>
A simple configuration to modify limits, gameplay and other. When a value is set to 0, then this will remove the limit and function as unlimited.<br>
<br>
<div style="padding-left: 30px"><i>MaxLinksInServer: 0<br>
MaxRedstoneDevicesInServer: 0<br>
EnablePermissions: false<br>
MaxLinksPerPlayer: 0<br>
MaxLinkDistance: 0<br>
AllowCrossWorldSignal: true</i>&ZeroWidthSpace;</div><br>
<br>
<b><span style="font-size: 18px"><span style="color: #ff4d4d">API Support</span></span></b><br>
Although small and simple at the moment. This plugin supports a small number of API functions for other plugin developers to integrate and control WirelessRedstone.<br>
<br>
Import the plugin:<br>
<div style="padding-left: 30px"><i>import me.zato.wirelessredstone.api;</i>&ZeroWidthSpace;</div><br>
and access the api like such (example):<br>
<div style="padding-left: 30px"><i>api.getRedstoneSender();</i>&ZeroWidthSpace;</div><br>
<br>
<br>
<b><span style="font-size: 18px"><span style="color: #ff4d4d">Community Support</span></span></b><br>
If you have any further questions don't forget to contact me through discord or github.<br>
Satisfied? Please leave a review!<br>
<br>
Check out my Discord for suggestions, tips, questions and other support.<br>
<a href="https://discord.gg/TsRTa7BUWm" target="_blank" class="externalLink" rel="nofollow">https://discord.gg/TsRTa7BUWm</a>
</blockquote></s>

