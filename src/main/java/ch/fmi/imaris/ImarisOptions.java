
package ch.fmi.imaris;

import java.io.File;

import org.scijava.menu.MenuConstants;
import org.scijava.options.OptionsPlugin;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

@Plugin(type = OptionsPlugin.class,
		menu = {
			@Menu(
				label = MenuConstants.EDIT_LABEL,
				weight = MenuConstants.EDIT_WEIGHT,
				mnemonic = MenuConstants.EDIT_MNEMONIC),
			@Menu(label = "Options"),
			@Menu(label = "Imaris Converter...")})
public class ImarisOptions extends OptionsPlugin {

	@Parameter(label = "Location of ImarisConvert.exe")
	private File imarisConvert = new File("C:\\Program Files\\Bitplane\\ImarisFileConverter 9.5.1\\ImarisConvert.exe");

	public File getImarisConvertPath() {
		return imarisConvert;
	}
}
