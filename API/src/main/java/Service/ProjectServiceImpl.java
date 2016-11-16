package Service;

import DAO.ProjectDAO;
import DAO.ProjectDAOImpl;
import Model.Project;
import Model.User;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public class ProjectServiceImpl implements ProjectService {
    ProjectDAO projectDAO;

    public ProjectServiceImpl(){
        projectDAO = new ProjectDAOImpl(APIService.em);
    }

    public boolean addEntity(Project project) throws Exception{
        return projectDAO.addEntity(project);
    }
    public Project getEntityById(Long id) throws Exception{
        return projectDAO.getEntityById(id);

    }
    public List getEntityList(User user) throws Exception{
        return projectDAO.getEntityList(user);
    }
    public boolean deleteEntity(Long id) throws Exception{
        return projectDAO.deleteEntity(id);
    }

}