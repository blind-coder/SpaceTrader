/*
 * Copyright (c) 2014 Benjamin Schieder
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.anderdonau.spacetrader.DataTypes;

import de.anderdonau.spacetrader.GameState;

public class SpecialEvents {
	public static SpecialEvent[] mSpecialEvent = {new SpecialEvent("Dragonfly Destroyed",
		"Hello, Commander. This is Colonel Jackson again. On behalf of the Space Corps, I thank you for your valuable assistance in destroying the Dragonfly. As a reward, we will install one of the experimental shields on your ship. Return here for that when you're ready.",
		0, 0, true), new SpecialEvent("Weird Ship",
		"A small ship of a weird design docked here recently for repairs. The engineer who worked on it said that it had a weak hull, but incredibly strong shields. I heard it took off in the direction of the Melina system.",
		0, 0, true), new SpecialEvent("Lightning Ship",
		"A ship with shields that seemed to be like lightning recently fought many other ships in our system. I have never seen anything like it before. After it left, I heard it went to the Regulas system.",
		0, 0, true), new SpecialEvent("Strange Ship",
		"A small ship with shields like I have never seen before was here a few days ago. It destroyed at least ten police ships! Last thing I heard was that it went to the Zalkon system.",
		0, 0, true), new SpecialEvent("Monster Killed",
		"We thank you for destroying the space monster that circled our system for so long. Please accept 15000 credits as reward for your heroic deed.",
		-15000, 0, true), new SpecialEvent("Medicine Delivery",
		"Thank you for delivering the medicine to us. We don't have any money to reward you, but we do have an alien fast-learning machine with which we will increase your skills.",
		0, 0, true), new SpecialEvent("Retirement",
		"Welcome to the Utopia system. Your own moon is available for you to retire to it, if you feel inclined to do that. Are you ready to retire and lead a happy, peaceful, and wealthy life?",
		0, 0, false), new SpecialEvent("Moon For Sale",
		"There is a small but habitable moon for sale in the Utopia system, for the very reasonable sum of half a million credits. If you accept it, you can retire to it and live a peaceful, happy, and wealthy life. Do you wish to buy it?",
		GameState.COSTMOON, 4, false), new SpecialEvent("Skill Increase",
		"An alien with a fast-learning machine offers to increase one of your skills for the reasonable sum of 3000 credits. You won't be able to pick that skill, though. Do you accept his offer?",
		3000, 3, false), new SpecialEvent("Merchant Prince",
		"A merchant prince offers you a very special and wondrous item for the sum of 1000 credits. Do you accept?",
		1000, 1, false), new SpecialEvent("Erase Record",
		"A hacker conveys to you that he has cracked the passwords to the galaxy-wide police computer network, and that he can erase your police record for the sum of 5000 credits. Do you want him to do that?",
		5000, 3, false), new SpecialEvent("Tribble Buyer",
		"An eccentric alien billionaire wants to buy your collection of tribbles and offers half a credit for each of them. Do you accept his offer?",
		0, 3, false), new SpecialEvent("Space Monster",
		"A space monster has invaded the Acamar system and is disturbing the trade routes. You'll be rewarded handsomely if you manage to destroy it.",
		0, 1, true), new SpecialEvent("Dragonfly",
		"This is colonel Jackson of the Space Corps. An experimental ship, code-named \"Dragonfly\", has been stolen. It is equipped with very special, almost indestructible shields. It shouldn't fall into the wrong hands and we will reward you if you destroy it. It has been last seen in the Baratas system.",
		0, 1, true), new SpecialEvent("Cargo For Sale",
		"A trader in second-hand goods offers you 3 sealed cargo canisters for the sum of 1000 credits. It could be a good deal: they could contain robots. Then again, it might just be water. Do you want the canisters?",
		1000, 3, false), new SpecialEvent("Lightning Shield",
		"Colonel Jackson here. Do you want us to install a lightning shield on your current ship?", 0,
		0, false), new SpecialEvent("Japori Disease",
		"A strange disease has invaded the Japori system. We would like you to deliver these ten canisters of special antidote to Japori. Note that, if you accept, ten of your cargo bays will remain in use on your way to Japori. Do you accept this mission?",
		0, 1, false), new SpecialEvent("Lottery Winner",
		"You are lucky! While docking on the space port, you receive a message that you won 1000 credits in a lottery. The prize had been added to your account.",
		-1000, 0, true), new SpecialEvent("Artifact Delivery",
		"This is professor Berger. I thank you for delivering the alien artifact to me. I hope the aliens weren't too much of a nuisance. I have transferred 20000 credits to your account, which I assume compensates for your troubles.",
		-20000, 0, true), new SpecialEvent("Alien Artifact",
		"This alien artifact should be delivered to professor Berger, who is currently traveling. You can probably find him at a hi-tech solar system. The alien race which produced this artifact seems keen on getting it back, however, and may hinder the carrier. Are you, for a price, willing to deliver it?",
		0, 1, false), new SpecialEvent("Ambassador Jarek",
		"A recent change in the political climate of this solar system has forced ambassador Jarek to flee back to his home system, Devidia. Would you be willing to give him a lift?",
		0, 1, false), new SpecialEvent("Alien Invasion",
		"We received word that aliens will invade Gemulon seven days from now. We know exactly at which coordinates they will arrive, but we can't warn Gemulon because an ion storm disturbs all forms of communication. We need someone, anyone, to deliver this info to Gemulon within six days.",
		0, 0, true), new SpecialEvent("Gemulon Invaded",
		"Alas, Gemulon has been invaded by aliens, which has thrown us back to pre-agricultural times. If only we had known the exact coordinates where they first arrived at our system, we might have prevented this tragedy from happening.",
		0, 0, true), new SpecialEvent("Fuel Compactor",
		"Do you wish us to install the fuel compactor on your current ship? (You need a free gadget slot)",
		0, 0, false), new SpecialEvent("Dangerous Experiment",
		"While reviewing the plans for Dr. Fehler's new space-warping drive, Dr. Lowenstam discovered a critical error. If you don't go to Daled and stop the experiment within ten days, the time-space continuum itself could be damaged!",
		0, 0, true), new SpecialEvent("Jonathan Wild",
		"Law Enforcement is closing in on notorious criminal kingpin Jonathan Wild. He would reward you handsomely for smuggling him home to Kravat. You'd have to avoid capture by the Police on the way. Are you willing to give him a berth?",
		0, 1, false), new SpecialEvent("Morgan's Reactor",
		"Galactic criminal Henry Morgan wants this illegal ion reactor delivered to Nix. It's a very dangerous mission! The reactor and its fuel are bulky, taking up 15 bays. Worse, it's not stable -- its resonant energy will weaken your shields and hull strength while it's aboard your ship. Are you willing to deliver it?",
		0, 0, false), new SpecialEvent("Install Morgan's Laser",
		"Morgan's technicians are standing by with something that looks a lot like a military laser -- if you ignore the additional cooling vents and anodized ducts. Do you want them to install Morgan's special laser?",
		0, 0, false), new SpecialEvent("Scarab Stolen",
		"Captain Renwick developed a new organic hull material for his ship which cannot be damaged except by Pulse lasers. While he was celebrating this success, pirates boarded and stole the craft, which they have named the Scarab. Rumors suggest it's being hidden at the exit to a wormhole. Destroy the ship for a reward!",
		0, 1, true), new SpecialEvent("Upgrade Hull",
		"The organic hull used in the Scarab is still not ready for day-to-day use. But Captain Renwick can certainly upgrade your hull with some of his retrofit technology. It's light stuff, and won't reduce your ship's range. Should he upgrade your ship?",
		0, 0, false), new SpecialEvent("Scarab Destroyed",
		"Space Corps is indebted to you for destroying the Scarab and the pirates who stole it. As a reward, we can have Captain Renwick upgrade the hull of your ship. Note that his upgrades won't be transferable if you buy a new ship! Come back with the ship you wish to upgrade.",
		0, 0, true), new SpecialEvent("Reactor Delivered",
		"Henry Morgan takes delivery of the reactor with great glee. His men immediately set about stabilizing the fuel system. As a reward, Morgan offers you a special, high-powered laser that he designed. Return with an empty weapon slot when you want them to install it.",
		0, 0, true), new SpecialEvent("Jarek Gets Out",
		"Ambassador Jarek is very grateful to you for delivering him back to Devidia. As a reward, he gives you an experimental handheld haggling computer, which allows you to gain larger discounts when purchasing goods and equipment.",
		0, 0, true), new SpecialEvent("Gemulon Rescued",
		"This information of the arrival of the alien invasion force allows us to prepare a defense. You have saved our way of life. As a reward, we have a fuel compactor gadget for you, which allows you to travel 18 parsecs with any ship. Return here to get it installed.",
		0, 0, true), new SpecialEvent("Disaster Averted",
		"Upon your warning, Dr. Fehler calls off the experiment. As your  reward, you are given a Portable Singularity. This device will, for one time only, instantaneously transport you to any system in the galaxy. The Singularity can be accessed by clicking the \"J\" (Jump) button on the Galactic Chart.",
		0, 0, true), new SpecialEvent("Experiment Failed",
		"Dr. Fehler can't understand why the experiment failed. But the failure has had a dramatic and disastrous effect on the fabric of space-time itself. It seems that Dr. Fehler won't be getting tenure any time soon... and you may have trouble when you warp!",
		0, 0, true), new SpecialEvent("Wild Gets Out",
		"Jonathan Wild is most grateful to you for spiriting him to safety. As a reward, he has one of his Cyber Criminals hack into the Police Database, and clean up your record. He also offers you the opportunity to take his talented nephew Zeethibal along as a Mercenary with no pay.",
		0, 0, true)};

	public static class SpecialEvent {
		public String  title;
		public String  questStringID;
		public int     price;
		public int     occurrence;
		public boolean justAMessage;

		public SpecialEvent(String title, String questStringID, int price, int occurrence, boolean justAMessage) {
			this.title = title;
			this.questStringID = questStringID;
			this.price = price;
			this.occurrence = occurrence;
			this.justAMessage = justAMessage;
		}
	}
}
