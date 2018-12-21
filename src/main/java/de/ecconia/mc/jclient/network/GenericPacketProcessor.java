package de.ecconia.mc.jclient.network;

import de.ecconia.mc.jclient.PrimitiveDataDude;
import de.ecconia.mc.jclient.gui.monitor.L;
import de.ecconia.mc.jclient.network.packeting.GenericPacket;
import de.ecconia.mc.jclient.network.packeting.PacketThread;
import old.packet.MessageBuilder;
import old.reading.helper.Provider;

public class GenericPacketProcessor extends PacketThread
{
	public GenericPacketProcessor(String name, PrimitiveDataDude dataDude)
	{
		super(name, dataDude);
	}

	@Override
	protected void process(GenericPacket packet)
	{
		Provider p = packet.getProvider();
		int id = packet.getId();
		
		if(id == 3)
		{
			logPacket("Compression request");
			int compressionLevel = p.readCInt();
			logData("> Compression above " + compressionLevel + " bytes.");
			if(p.remainingBytes() > 0)
			{
				logData("> WARNING: Compression package had more content.");
			}
			
			con.setCompression(compressionLevel);
		}
		else if(id == 27)
		{
			logPacket("Disconnected by server");
			logData("Disconnected by Server: " + p.readString());
		}
		else if(id == 33)
		{
			logPacket("Ping");
			byte[] ping = p.readBytes(8);
			
			MessageBuilder mb = new MessageBuilder();
			mb.addBytes(ping);
			mb.prependCInt(14);
			con.sendPacket(mb.asBytes());
		}
		else if(id == 14)
		{
			logPacket("Chat");
			String jsonMessage = p.readString();
			logData("Message in " + p.readByte() + ": " + jsonMessage);
			
			dataDude.newChatJSON(jsonMessage);
		}
		else if(id == 74)
		{
			logPacket("WorldTime");
			
		}
		else if(id == 13)
		{
			logPacket("Server difficulty");
			//UByte -> difficulty
		}
		else if(id == 0x2e)
		{
			logPacket("Player abilities");
			//Byte -> Invunerable/Flying/AllowFlying/InstaBreak
			//Float -> Fly speed
			//Float -> FOV (movement speed)
		}
		else if(id == 0x3d)
		{
			logPacket("Inventory Slot selected");
		}
		else if(id == 0x54)
		{
			logPacket("Declare Receipts (big packet)");
			//CInt -> Amount
			//[
			//String -> Identifier
			//String -> Where
			//.....
			//]
		}
		else if(id == 0x55)
		{
			logPacket("\"Tags\"");
			//Mapping for BlockID <-> BlockIdentifier?
		}
		else if(id == 0x1c)
		{
			logPacket("Entity status");
		}
		else if(id == 0x11)
		{
			logPacket("Declare command (:@)");
		}
		else if(id == 0x34)
		{
			logPacket("Unlock receipts");
		}
		else if(id == 0x47)
		{
			logPacket("Teams");
		}
		else if(id == 0x4e)
		{
			logPacket("Header/Footer PlayerList");
		}
		else if(id == 0x30)
		{
			logPacket("Player entry");
		}
		else if(id == 0x3F)
		{
			logPacket("Entity metadata");
		}
		else if(id == 0x22)
		{
			logPacket("Chunk Data");
		}
		else if(id == 0x02)
		{
			logPacket("Spawn entity (thunderbolt)???");
			//CInt -> EntityID
			//Byte -> Type
			//Double -> x
			//Double -> y
			//Double -> z
		}
		else if(id == 0x44)
		{
			logPacket("Update health");
		}
		else if(id == 0x43)
		{
			logPacket("Set XP");
		}
		else if(id == 25)
		{
			logPacket("\"Plugin\" Message");
			//String -> channel
			//Byte[] -> data
		}
		else if(id == 0x51)
		{
			logPacket("Advancements");
			//Dough - big one
		}
		else if(id == 0x52)
		{
			logPacket("Entity Properties");
		}
		else if(id == 0x15)
		{
			logPacket("Window item update");
		}
		else if(id == 0x17)
		{
			logPacket("Set Inventory Slot");
		}
		else if(id == 0x49)
		{
			logPacket("Spawn Position");
		}
		else if(id == 0x32)
		{
			logPacket("Player Location/Head");
		}
		else if(id == 0x3b)
		{
			logPacket("World Border");
		}
		else if(id == 37)
		{
			logPacket("JoinPlayerState");
			//Int -> ID
			//UByte -> Gamemode
			//Int -> Dimension Type
			//UByte -> Difficulty
			//UByte -> Max players (ignored)
			//String -> Map type...?
			//Boolean -> Show Debug
		}
		else
		{
			logPacket("||> Packet: ID:" + id + "(0x" + (Integer.toHexString(id)) + ")" + " Size:" + p.remainingBytes());
		}
	}
	
	private void logPacket(String name)
	{
//		System.out.println(">>> P: " + name);
		L.writeLineOnChannel("Gen: Packets", name);
	}
	
	private void logData(String message)
	{
		L.writeLineOnChannel("Content", message);
	}
}