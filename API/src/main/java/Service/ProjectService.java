package Service;

import Model.Project;
import Model.User;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public interface ProjectService {
    boolean addEntity(Project project) throws Exception;
    Project getEntityById(Long id) throws Exception;
    List getEntityList(User user) throws Exception;
    boolean deleteEntity(Long id) throws Exception;
}
