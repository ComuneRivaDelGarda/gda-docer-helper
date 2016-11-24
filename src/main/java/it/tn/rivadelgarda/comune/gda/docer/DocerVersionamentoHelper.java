package it.tn.rivadelgarda.comune.gda.docer;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import it.kdm.docer.webservices.DocerServicesStub;
import it.kdm.docer.webservices.DocerServicesStub.AddNewVersion;
import it.kdm.docer.webservices.DocerServicesStub.AddNewVersionResponse;
import it.kdm.docer.webservices.DocerServicesStub.GetVersions;
import it.kdm.docer.webservices.DocerServicesStub.GetVersionsResponse;

public class DocerVersionamentoHelper extends AbstractDocerHelper {

	public DocerVersionamentoHelper(String docerSerivcesUrl, String docerUsername, String docerPassword) {
		super(docerSerivcesUrl, docerUsername, docerPassword);
	}

	/**
	 * Questo metodo permette di creare una nuova versione del file (o documento
	 * elettronico) di un Documento nel DMS. Il metodo è applicabile per la sola
	 * gestione del versioning standard.
	 * 
	 * @param documentsId
	 *            id del Documento
	 * @param file
	 *            La nuova versione del file o documento elettronico
	 * @return Il version number della versione creata
	 * @throws Exception
	 */
	public String addNewVersion(String documentsId, File file) throws Exception {
		FileDataSource fileDataSource = new FileDataSource(file);
		DataHandler dataHandler = new DataHandler(fileDataSource);

		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		AddNewVersion request = new AddNewVersion();
		request.setToken(getLoginTicket());
		request.setDocId(documentsId);
		request.setFile(dataHandler);
		AddNewVersionResponse response = service.addNewVersion(request);
		String version = response.get_return();
		return version;
	}

	/**
	 * Questo metodo permette di recuperare la lista dei version number ovvero
	 * delle versioni dei file di un Documento del DMS. Il metodo è applicabile
	 * per la sola gestione del versioning standard.
	 * 
	 * @param documentsId
	 *            id del Documento di riferimento
	 * @return collezione dei version number
	 * @throws Exception
	 */
	public String[] getVersions(String documentsId) throws Exception {
		DocerServicesStub service = new DocerServicesStub(docerSerivcesUrl + DocerServices);
		GetVersions request = new GetVersions();
		request.setToken(getLoginTicket());
		request.setDocId(documentsId);
		GetVersionsResponse response = service.getVersions(request);
		String[] versions = response.get_return();
		return versions;
	}

}
