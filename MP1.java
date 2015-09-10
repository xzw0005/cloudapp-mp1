import java.io.File;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    public String[] process() throws Exception {
        String[] ret = new String[20];
       
		//TODO
		//List<String> allLines = Files.readAllLines(Paths.get(this.inputFileName), StandardCharsets.UTF_8);
		File inputFile = new File(this.inputFileName);
		Scanner scanner = new Scanner(file);
		
		
		Map<String, Integer> wordCounts = new HashMap<String, Integer>();
		
		for (int i: this.getIndexes()) {
			StringTokenizer st = new StringTokenizer(allLines.get(i), this.delimiters, false);
			
			while (st.hasMoreTokens()) {
				String word = st.nextToken().toLowerCase().trim();
				if (word == "" || this.stopWords.contains(word))
					continue;
				
				Integer count = wordCounts.get(word);
				if (count == null) 
					wordCounts.put(word, 1);
				else
					wordCounts.put(word, count + 1);
			}
		}
		
		//Map<String, Integer> sortedMap = sortByValues(sortByKeys(wordCounts));
		Map<String, Integer> keySorted = new TreeMap<String, Integer>(wordCounts); // sort a map by keys
		Map<String, Integer> sortedMap = sortByValues(keySorted);		
		
		Set<String> keys = sortedMap.keySet();
		String[] keyArray = keys.toArray(new String[keys.size()]);
		ret = Arrays.copyOf(keyArray, 20);		
		//END TODO

        return ret;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
	
	/*
	private static Map<String, Integer> sortByKeys(Map<String, Integer> rawMap) {
		List<Map.Key<String, Integer>> keyList = new LinkedList<Map.Key<String, Integer>>(rawMap.keySet());
		Collections.sort(keyList);
		Map<String, Integer> keySorted = new LinkedHashMap<String, Integer>();
		for (Map.Key<String, Integer> key: keyList) {
			keySorted.put(key, rawMap.get(key));
		}
		return keySorted;
	}
	*/
	
	private static Map<String, Integer> sortByValues(Map<String, Integer> unsortMap) {
		// Convert Map to List
		List<Map.Entry<String, Integer>> valList = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
		// Sort list with comparator, to compare the Map values
		Collections.sort(
			valList, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					return (o2.getValue()).compareTo(o1.getValue());	// for descending order
				}
			}
		);
		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		//for (Iterator<Map.Entry<String, Integer>> iter = valList.iterator(); iter.hasNext();) {
			//Map.Entry<String, Integer> entry = iter.next(); 
		for (Map.Entry<String, Integer> entry: valList) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

}
