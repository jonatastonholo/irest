package br.cefetmg.es.irest.model.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;

public final class ImagemUtils {

	public static final String TMP_PATH_IMAGE = "/public/item/images/tmp/";

	public static final String PATH_IMAGE = "/public/item/images/";

	public static String getCompleteImageDir(FacesContext context, String bdDir) {
		String dir = "";
		dir = PATH_IMAGE;
		dir += "/" + bdDir;
		return dir;
	}

	public static String getContextPath() {
		return ((javax.servlet.ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("");
	}

	public static void removerTmpDir() {
		File diretorio = new File(getContextPath() + ImagemUtils.TMP_PATH_IMAGE);
		if (diretorio.exists()) {
			diretorio.delete();
		}
	}

	public static void removeTmpImage(String imageName) {
		File file = new File(getContextPath() + ImagemUtils.TMP_PATH_IMAGE + imageName);
		if (file.exists()) {
			file.delete();
		}
	}

	public static void removeOldImage(String imageName) {
		File file = new File(getContextPath() + ImagemUtils.PATH_IMAGE + imageName);
		if (file.exists()) {
			file.delete();
		}
	}

	public static void salvarImagemDiretorioTmp(String nomeArquivo,
			InputStream is, FacesContext context) {
		FileOutputStream out = null;
		try {
			File diretorio = new File(getContextPath() + ImagemUtils.TMP_PATH_IMAGE);

			if (!diretorio.exists()) {
				diretorio.mkdir();
			}

			File file = new File(diretorio, nomeArquivo);
			out = new FileOutputStream(file);

			// Imagens de até 5 megas !!
			byte[] buffer = new byte[1024 * 5];
			int nLidos;
			while ((nLidos = is.read(buffer)) >= 0) {
				out.write(buffer, 0, nLidos);
			}

			out.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void salvarImagem(String nomeArquivo, InputStream is, FacesContext context) {
		FileOutputStream out = null;
		try {
			File diretorio = new File(getContextPath() + ImagemUtils.PATH_IMAGE);

			if (!diretorio.exists()) {
				diretorio.mkdir();
			}

			File file = new File(diretorio, nomeArquivo);
			out = new FileOutputStream(file);

			// Imagens de até 5 megas !!
			byte[] buffer = new byte[1024 * 5];
			int nLidos;
			while ((nLidos = is.read(buffer)) >= 0) {
				out.write(buffer, 0, nLidos);
			}

			out.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getNomeImagem(String dir) {
		String nome = "";

		List<String> ldir = Arrays.asList(dir.split("/"));

		nome = ldir.get(ldir.size()-1);

		return nome;
	}
}
