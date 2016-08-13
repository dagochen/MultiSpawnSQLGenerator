import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class MultiSpawnSQLGenerator {
	public static void main(String[] args) {

		final String SEPERATOR = ";";
		int startPoolEntry = 26000;
		String INSERT = "INSERT INTO `pool_gameobject` (`guid`, `pool_entry`, `chance`, `description`) VALUES ('";
		String INSERTPT = "INSERT INTO `pool_template` (`entry`, `max_limit`, `description`)VALUES('";
		String sourceFile = "./files/fishing.csv";
		String targetFile = "./files/fishing_pool.sql";
		Map<String, List<GameObject>> eingeleseneGOs = new HashMap<>();
		BufferedReader reader = null;
		FileWriter writer = null;
		
		try 
		{
			reader = new BufferedReader(new FileReader(sourceFile));
			writer = new FileWriter(targetFile, false);
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, SEPERATOR); // name;guid;id;map;x;y;z
				String name = tokenizer.nextToken();

				int guid = Integer.valueOf(tokenizer.nextToken());
				int id = Integer.valueOf(tokenizer.nextToken());
				int map = Integer.valueOf(tokenizer.nextToken());
				String x = tokenizer.nextToken();
				String y = tokenizer.nextToken();
				String z = tokenizer.nextToken();

				GameObject g = new GameObject(name, guid, id, map, x, y, z);

				if (eingeleseneGOs.containsKey(g.getPosition().getString())) {
					List<GameObject> vorhandenListe = eingeleseneGOs.get(g.getPosition().getString());
					vorhandenListe.add(g);
				} 
				else 
				{
					List<GameObject> neueListe = new ArrayList<>();
					neueListe.add(g);
					eingeleseneGOs.put(g.getPosition().getString(), neueListe);
				}
			}
			reader.close();

			for (String posi : eingeleseneGOs.keySet()) {
				List<GameObject> goList = eingeleseneGOs.get(posi);
				// dont pool if only one go is at that position
				if (goList.size() == 1) {
					continue;
				}

				writer.write("-- ------------------ Entry " + startPoolEntry + "  --------------------------- "+ System.getProperty("line.separator"));
				for (GameObject go : goList) {
					String insert = INSERT + go.getGuid() + "', '" + startPoolEntry + "', '" + 0 + "', '" + go.getString()
							+ "');" + System.getProperty("line.separator");
					writer.write(insert);

				}
				String poolTemplate = INSERTPT + startPoolEntry + "', '1', 'Fishing Hole Multinode - " + posi + "'); " + System.getProperty("line.separator");
				writer.write(poolTemplate);
				writer.write(System.getProperty("line.separator"));
				startPoolEntry++;
			}

			writer.close();

		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		

	}}
