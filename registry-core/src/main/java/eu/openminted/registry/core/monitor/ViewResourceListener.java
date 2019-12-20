package eu.openminted.registry.core.monitor;

import eu.openminted.registry.core.domain.Resource;
import eu.openminted.registry.core.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViewResourceListener implements ResourceListener{


    @Autowired
    private ViewService viewService;

    @Override
    public void resourceAdded(Resource resource) {
        viewService.updateView(resource.getResourceTypeName());
    }

    @Override
    public void resourceUpdated(Resource previousResource, Resource newResource) {
        viewService.updateView(newResource.getResourceTypeName());
        if(!newResource.getResourceTypeName().equals(previousResource.getResourceTypeName()))
            viewService.updateView(previousResource.getResourceTypeName());
    }

    @Override
    public void resourceDeleted(Resource resource) {
        viewService.updateView(resource.getResourceTypeName());
    }
}
