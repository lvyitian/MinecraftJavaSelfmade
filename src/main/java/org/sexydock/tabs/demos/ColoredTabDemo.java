/*
Copyright 2012 James Edwards

This file is part of Jhrome.

Jhrome is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Jhrome is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with Jhrome.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sexydock.tabs.demos;

import java.awt.Color;

import org.sexydock.tabs.DefaultTabbedPaneWindow;
import org.sexydock.tabs.Tab;
import org.sexydock.tabs.TestTabFactory;
import org.sexydock.tabs.jhrome.JhromeTabUI;
import org.sexydock.tabs.jhrome.JhromeTabbedPaneUI;

public class ColoredTabDemo implements ISexyTabsDemo
{
	@Override
	public void start()
	{
		DefaultTabbedPaneWindow window = new DefaultTabbedPaneWindow(getClass().getSimpleName());
		
		TestTabFactory tabFactory = new TestTabFactory();
		window.getTabbedPane().putClientProperty(JhromeTabbedPaneUI.TAB_FACTORY, tabFactory);
		
		JhromeTabbedPaneUI tabbedPaneUI = (JhromeTabbedPaneUI) window.getTabbedPane().getUI();
		Tab tab1 = tabbedPaneUI.getTabFactory().createTabWithContent();
		JhromeTabUI ui = new JhromeTabUI();
		ui.getSelectedAttributes().topColor = Color.RED;
		ui.getRolloverAttributes().topColor = Color.BLUE;
		tab1.setUI(ui);
		tabbedPaneUI.addTab(0, tab1, false);
		window.getTabbedPane().setSelectedIndex(0);
		
		window.setSize(800, 600);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
