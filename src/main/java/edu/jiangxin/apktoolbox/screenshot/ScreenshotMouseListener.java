package edu.jiangxin.apktoolbox.screenshot;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import edu.jiangxin.apktoolbox.utils.Utils;

public class ScreenshotMouseListener extends MouseAdapter {
	@Override
	public void mousePressed(MouseEvent e) {
		super.mouseClicked(e);
		ScreenShotFrame screenShotJFrame = new ScreenShotFrame();
		screenShotJFrame.setVisible(true);
		Utils.setJFrameCenterInScreen(screenShotJFrame);
	}
}
