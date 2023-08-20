package net.swade.permissionss;

import cn.nukkit.utils.Config;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class Profile {
    @Getter private final String name;
    @Getter @Setter private String group;
    @Getter @Setter private List<String> permissions;
    @Getter @Setter private long time;

    public static Profile getProfile(String name) {
        Config config = new Config(Main.getInstance().getPlayerConfigPath(), 1);
        return new Gson().fromJson(config.getString(name.toLowerCase()), Profile.class);
    }

    public static void save(String name, Profile profile){
        Config config = new Config(Main.getInstance().getPlayerConfigPath(), 1);
        config.set(name.toLowerCase(), profile.toString());
        config.save();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
