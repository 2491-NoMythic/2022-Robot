// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.CargoState;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.SPILink;

public class Pixy2SubSystem extends SubsystemBase {
	private static final int BLUE = 1;
	private static final int RED = 2;
	private static final int MIN_SIZE = 50;

	private Pixy2 pixy;
	private Block blockLeft;
	private Block blockRight;

	public Pixy2SubSystem() {
		pixy = Pixy2.createInstance(new SPILink()); // Creates a new Pixy2 camera using SPILink
		pixy.init();
	}

	public void turnOnLights() {
		pixy.setLamp((byte) 1, (byte) 1);
	}

	public void turnOffLights() {
		pixy.setLamp((byte) 0, (byte) 0);
	}

	public CargoState getLeftCargoState() {
		return convert(blockLeft);
	}

	public CargoState getRightCargoState() {
		return convert(blockRight);
	}

	private CargoState convert(Block block) {
		if (block == null) {
			return CargoState.Empty;
		} else if (block.getSignature() == RED) {
			return CargoState.Red;
		}
		return CargoState.Blue;
	}

	@Override
	public void periodic() {
		scanForBlocks();
	}

	public void scanForBlocks() {
		blockLeft = null;
		blockRight = null;

		Block largestBlock = null;
		Block secondLargestBlock = null;

		pixy.getCCC().getBlocks(false);
		ArrayList<Block> blocks = pixy.getCCC().getBlockCache();
		
		if (blocks == null) {
			return;
		}

		for (Block block : blocks) {
			if ((block.getSignature() == RED || block.getSignature() == BLUE)  && block.getWidth() > MIN_SIZE) {
				if (largestBlock == null) {
					largestBlock = block;
				} else if (block.getWidth() > largestBlock.getWidth()) {
					secondLargestBlock = largestBlock;
					largestBlock = block;
				} else if (secondLargestBlock == null || block.getWidth() > secondLargestBlock.getWidth()) {
					secondLargestBlock = block;
				}
			}
		}
		if (largestBlock == null) {
			// nothing both left and right
		} else if (secondLargestBlock == null) {
			// one side is empty
			if (largestBlock.getX() < 315/2) {
				// on left side
				blockLeft = largestBlock;
			} else {
				blockRight = largestBlock;
			}
		} else if (largestBlock.getX() > secondLargestBlock.getX()) {
			// largest is on right
			blockRight = largestBlock;
			blockLeft = secondLargestBlock;
		} else {
			// largest is on left
			blockRight = secondLargestBlock;
			blockLeft = largestBlock;
		}
		// SmartDashboard.putString("Ball Left", convert(blockLeft).toString());
		// SmartDashboard.putString("Ball Right", convert(blockRight).toString());
	}
}
