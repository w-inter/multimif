package Service;

import Model.TemporaryFile;
import Util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 11/18/16.
 */
public interface TemporaryFileService {
    TemporaryFile getEntityByHash(String hashKey) throws DataException;
    List getEntityByUserProject (Long idUser, Long idProject) throws DataException;
    TemporaryFile getEntityById (Long idTemporaryFile) throws DataException;
    boolean exists (Long idFileTemporary);
    TemporaryFile addEntity(Long idUser, String content, String path, Long idProject)
            throws DataException;
    boolean deleteEntity(Long idFileTemporary) throws DataException;
}