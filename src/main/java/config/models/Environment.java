package config.models;

import java.util.ArrayList;
import java.util.List;

public class Environment {

    public EnvironmentModel dev;
    public EnvironmentModel test;
    public EnvironmentModel preprod;

    public EnvironmentModel getDev() {
        return dev;
    }

    public EnvironmentModel getTest() {
        return test;
    }

    public EnvironmentModel getPreprod() {
        return preprod;
    }

    public List<EnvironmentModel> getListOfEnvironments() {
        List<EnvironmentModel> environmentModels = new ArrayList<>();
        environmentModels.add(dev);
        environmentModels.add(test);
        environmentModels.add(preprod);
        return environmentModels;
    }
}

