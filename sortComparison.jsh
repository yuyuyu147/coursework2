ArrayList<Integer> cardCompare(String card1, String card2) {
    // 将卡片分为数字和花色
    int number1 = Integer.parseInt(card1.substring(0, card1.length() - 1));
    int number2 = Integer.parseInt(card2.substring(0, card2.length() - 1));
    char suit1 = card1.charAt(card1.length() - 1);
    char suit2 = card2.charAt(card2.length() - 1);

    // 定义花色优先级
    String suitPriority = "HCDS";

    // 比较花色
    if (suitPriority.indexOf(suit1) < suitPriority.indexOf(suit2)) {
        return new ArrayList<>(List.of(-1));
    } else if (suitPriority.indexOf(suit1) > suitPriority.indexOf(suit2)) {
        return new ArrayList<>(List.of(1));
    } else {
        // 如果花色相同，则比较数字
        if (number1 < number2) {
            return new ArrayList<>(List.of(-1));
        } else if (number1 > number2) {
            return new ArrayList<>(List.of(1));
        } else {
            return new ArrayList<>(List.of(0));
        }
    }
}


ArrayList<String> bubbleSort(ArrayList<String> array) {
    int n = array.size();
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            // 比较相邻元素
            if (cardCompare(array.get(j), array.get(j + 1)).get(0) > 0) {
                // 交换元素
                String temp = array.get(j);
                array.set(j, array.get(j + 1));
                array.set(j + 1, temp);
            }
        }
    }
    return array;
}


ArrayList<String> mergeSort(ArrayList<String> array) {
    if (array.size() <= 1) {
        return array;
    }

    // 分割数组
    int mid = array.size() / 2;
    ArrayList<String> left = new ArrayList<String>(array.subList(0, mid));
    ArrayList<String> right = new ArrayList<String>(array.subList(mid, array.size()));

    // 递归排序左右子数组
    left = mergeSort(left);
    right = mergeSort(right);

    // 合并排序后的子数组
    return merge(left, right);
}

// 合并两个已排序的数组
ArrayList<String> merge(ArrayList<String> left, ArrayList<String> right) {
    ArrayList<String> result = new ArrayList<String>();
    int i = 0, j = 0;

    // 合并过程：比较左右数组的元素并按顺序插入
    while (i < left.size() && j < right.size()) {
        if (cardCompare(left.get(i), right.get(j)).get(0) <= 0) {
            result.add(left.get(i));
            i++;
        } else {
            result.add(right.get(j));
            j++;
        }
    }

    // 如果左数组还有剩余元素
    while (i < left.size()) {
        result.add(left.get(i));
        i++;
    }

    // 如果右数组还有剩余元素
    while (j < right.size()) {
        result.add(right.get(j));
        j++;
    }

    return result;
}


long measureBubbleSort(String filename) throws IOException {
    // 读取文件内容并将每行作为扑克牌添加到 ArrayList 中
    ArrayList<String> list = new ArrayList<String>();
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    
    while ((line = reader.readLine()) != null) {
        list.add(line.trim());
    }
    reader.close();

    // 记录当前时间
    long startTime = System.currentTimeMillis();
    
    // 执行冒泡排序
    bubbleSort(list);
    
    // 记录排序后的时间
    long endTime = System.currentTimeMillis();
    
    // 返回排序所用的时间（毫秒）
    return endTime - startTime;
}


long measureMergeSort(String filename) throws IOException {
    // 读取文件内容并将每行作为扑克牌添加到 ArrayList 中
    ArrayList<String> list = new ArrayList<String>();
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    
    while ((line = reader.readLine()) != null) {
        list.add(line.trim());
    }
    reader.close();

    // 记录当前时间
    long startTime = System.currentTimeMillis();
    
    // 执行归并排序
    mergeSort(list);
    
    // 记录排序后的时间
    long endTime = System.currentTimeMillis();
    
    // 返回排序所用的时间（毫秒）
    return endTime - startTime;
}


void sortComparison(String[] filenames) throws IOException {
    // 创建一个文件来保存结果
    FileWriter writer = new FileWriter("sortComparison.csv");
    BufferedWriter bufferedWriter = new BufferedWriter(writer);
    
    // 写入CSV文件头部，文件中列出每个文件包含的卡片数量
    bufferedWriter.write(", ");
    for (String filename : filenames) {
        // 获取每个文件中卡片的数量
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int lineCount = 0;
        while (br.readLine() != null) {
            lineCount++;
        }
        br.close();
        // 写入卡片数量到CSV
        bufferedWriter.write(lineCount + ", ");
    }
    bufferedWriter.newLine();

    // 写入冒泡排序的执行时间
    bufferedWriter.write("bubbleSort, ");
    for (String filename : filenames) {
        // 测量冒泡排序执行时间
        long bubbleTime = measureBubbleSort(filename);
        bufferedWriter.write(bubbleTime + ", ");
    }
    bufferedWriter.newLine();

    // 写入归并排序的执行时间
    bufferedWriter.write("mergeSort, ");
    for (String filename : filenames) {
        // 测量归并排序执行时间
        long mergeTime = measureMergeSort(filename);
        bufferedWriter.write(mergeTime + ", ");
    }
    bufferedWriter.newLine();

    // 关闭BufferedWriter
    bufferedWriter.close();
}

