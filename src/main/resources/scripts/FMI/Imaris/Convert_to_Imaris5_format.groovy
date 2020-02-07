#@ File (label="Input file") inputFile
#@ Boolean (label="Delete after conversion", value=true) delete
#@ OptionsService os
#@ PlatformService ps
#@ LogService log

import ch.fmi.imaris.ImarisOptions

ic = os.getOptions(ImarisOptions.class).getImarisConvertPath()
filename = inputFile.getName()
outputFile = new File(inputFile.getParentFile(), filename.substring(0, filename.lastIndexOf(".")) + ".ims")

log.info("Converting...")
log.info("  | from: " + inputFile.getAbsolutePath())
log.info("  |   to: " + outputFile.getAbsolutePath())

err = ps.exec(ic.getAbsolutePath(), "-i", inputFile.getAbsolutePath(), "-of", "Imaris5", "-o", outputFile.getAbsolutePath())

if (err != 0) {
	log.error("Conversion failed.")
	return
}
if (delete) {
	log.info("Deleting...")
	// also delete ics/ids companion file
	if (filename.substring(filename.lastIndexOf(".")) ==~ /\.i[cd]s/) {
		icsFile = new File(inputFile.getParentFile(), filename.substring(0, filename.lastIndexOf(".")) + ".ics")
		idsFile = new File(inputFile.getParentFile(), filename.substring(0, filename.lastIndexOf(".")) + ".ids")
		log.info("  |       " + icsFile.getAbsolutePath())
		icsFile.delete()
		log.info("  |       " + idsFile.getAbsolutePath())
		idsFile.delete()		
	} else {
		log.info("  |       " + inputFile.getAbsolutePath())
		inputFile.delete()
	}
}
log.info("Done.")
