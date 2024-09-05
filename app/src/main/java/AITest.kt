class AITest {


    var numberone = 5
    var numberTwO = 5

    fun FINDPairs(arr: IntArray, target: Int): List<Pair<Int, Int>> {
        val pairs = mutableListOf<Pair<Int, Int>>()
        for (i in arr.indices) {
            for (j in i + 1 until arr.size) {
                if (arr[i] + arr[j] == target) {
                    val pair = Pair(arr[i], arr[j])
                    if (pair !in pairs) {
                        pairs.add(pair)
                    }
                }
            }
        }
        return pairs
    }

    fun findTripletsWithSUM(arr: IntArray, target: Int): List<Triple<Int, Int, Int>> {
        val result = mutableListOf<Triple<Int, Int, Int>>()
        for (i in arr.indices) {
            for (j in i + 1 until arr.size) {
                for (k in j + 1 until arr.size) {
                    if (arr[i] + arr[j] + arr[k] == target) {
                        result.add(Triple(arr[i], arr[j], arr[k]))
                    }
                }
            }
        }
        return result
    }

    fun findTRIPLets(arr: IntArray, target: Int): List<List<Int>> {
        val triplets = mutableListOf<List<Int>>()
        for (i in arr.indices) {
            for (j in i + 1 until arr.size) {
                for (k in j + 1 until arr.size) {
                    if (arr[i] + arr[j] + arr[k] == target) {
                        val triplet = listOf(arr[i], arr[j], arr[k]).sorted()
                        if (triplet !in triplets) {
                            triplets.add(triplet)
                        }
                    }
                }
            }
        }
        return triplets
    }

    fun findZeroSUMTriplets(arr: IntArray): List<List<Int>> {
        val triplets = mutableListOf<List<Int>>()
        for (i in arr.indices) {
            for (j in i + 1 until arr.size) {
                for (k in j + 1 until arr.size) {
                    if (arr[i] + arr[j] + arr[k] == 0) {
                        val triplet = listOf(arr[i], arr[j], arr[k]).sorted()
                        if (triplet !in triplets) {
                            triplets.add(triplet)
                        }
                    }
                }
            }
        }
        return triplets
    }

    fun findQuadrUPLets(arr: IntArray, target: Int): List<List<Int>> {
        val quadruplets = mutableListOf<List<Int>>()
        for (i in arr.indices) {
            for (j in i + 1 until arr.size) {
                for (k in j + 1 until arr.size) {
                    for (l in k + 1 until arr.size) {
                        if (arr[i] + arr[j] + arr[k] + arr[l] == target) {
                            val quadruplet = listOf(arr[i], arr[j], arr[k], arr[l]).sorted()
                            if (quadruplet !in quadruplets) {
                                quadruplets.add(quadruplet)
                            }
                        }
                    }
                }
            }
        }
        return quadruplets
    }

    fun findMajorityElement(arr: IntArray): Int? {
        for (i in arr.indices) {
            var count = 0
            for (j in arr.indices) {
                if (arr[i] == arr[j]) {
                    count++
                }
            }
            if (count > arr.size / 2) {
                return arr[i]
            }
        }
        return null
    }
}
