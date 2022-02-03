package lnwwaveza008.lnwzadeath.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class tabcomplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        if (args.length == 1){
            if (sender instanceof Player){
                if (sender.hasPermission("lnwzadeath.admin")){
                    List<String> arguments = new ArrayList<>();
                    arguments.add("help");
                    arguments.add("gui");
                    arguments.add("set");
                    arguments.add("reload");
                    return arguments;
                }else{
                    List<String> arguments = new ArrayList<>();
                    arguments.add("help");
                    arguments.add("gui");
                    return arguments;
                }
            }
            else if (sender.isOp()) {
                List<String> arguments = new ArrayList<>();
                arguments.add("help");
                arguments.add("set");
                arguments.add("reload");
                return arguments;
            }
        }else if (args.length == 3){
            List<String> arguments = new ArrayList<>();
            arguments.add("<Amount>");
            return arguments;
        }
        return null;
    }
}