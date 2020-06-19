/*-
 * #%L
 * A collection of commands to interact with Imaris Converter
 * %%
 * Copyright (C) 2020 FMI Basel
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
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
