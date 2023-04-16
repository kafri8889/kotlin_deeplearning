import kotlin.random.Random

const val POPULATION_SIZE = 100
const val GENES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890, .-;:_!\"#%&/()=?@[]<>"

const val TARGET = "Rora Pacar Anap <3 <3"

// Create random genes for mutation
fun mutatedGenes(): Char {
    return GENES[Random.nextInt(0, GENES.length - 1)]
}

// create chromosome or string of genes
fun createGnome(): String {
    var gnome = ""

    for (i in TARGET.indices) {
        gnome += mutatedGenes()
    }

    return gnome
}

// Class representing individual in population
data class Individual(
    var chromosome: String,
    var fitness: Int
) {

    constructor(other: Individual): this(other.chromosome, other.fitness)

    constructor(chromosome: String): this(chromosome, 0) {
        fitness = callFitness()
    }

    // Perform mating and produce new offspring
    infix fun mate(parent: Individual): Individual {
        var childChromosome = ""

        for (i in chromosome.indices) {

            // Random probability
            val prob = Random.nextDouble(0.0, 1.0)

            when {
                // if prob is less than 0.45, insert gene
                // from parent 1
                prob < 0.45f -> {
                    childChromosome += chromosome[i]
                }
                // if prob is between 0.45 and 0.90, insert
                // gene from parent 2
                prob < 0.9f -> {
                    childChromosome += parent.chromosome[i]
                }
                // otherwise insert random gene(mutate),
                // for maintaining diversity
                else -> {
                    childChromosome += mutatedGenes()
                }
            }
        }

        return Individual(childChromosome)
    }

    fun callFitness(): Int {
        var mFitness = 0

        for (i in TARGET.indices) {
            if (chromosome[i] != TARGET[i]) {
                mFitness++
            }
        }

        return mFitness
    }
}

fun main() {
    var found = false

    // current generation
    var generation = 0

    val population = arrayListOf<Individual>()
    val sortedPopulation = arrayListOf<Pair<Int, Individual>>()

    // create initial population
    for (i in 0 until POPULATION_SIZE) {
        population.add(Individual(createGnome()))
    }

    while (!found) {
        // sort the population in increasing order of fitness score
       population.sortBy { it.fitness }

        // if the individual having lowest fitness score ie.
        // 0 then we know that we have reached to the target
        // and break the loop
        if(population[0].fitness <= 0) {
            found = true
        }

        // Otherwise, generate new offsprings for new generation
        val newGeneration = arrayListOf<Individual>()

        // Perform Elitism, that mean 10% of fittest population
        // goes to the next generation
        var s = (10 * POPULATION_SIZE) / 100

        for (i in 0 until s) {
            newGeneration.add(population[i])
        }

        // From 50% of fittest population, Individuals
        // will mate to produce offspring
        s = (90 * POPULATION_SIZE) / 100

        for (i in 0 until s) {
            val randomParent1 = Random.nextInt(0, 50)
            val randomParent2 = Random.nextInt(0, 50)

            val parent1 = Individual(population[randomParent1])
            val parent2 = Individual(population[randomParent2])

            val offspring = parent1 mate parent2

            newGeneration.add(offspring)
        }

        population.apply {
            clear()
            addAll(newGeneration)
        }

//        println()
//        println("Generation: $generation")
//        println("String: ${population[0].chromosome}")
//        println("Fitness: ${population[0].fitness}")
//        println()

        sortedPopulation.add(generation to population[0])

        generation++
    }

//    println()
//    println("Generation: $generation")
//    println("String: ${population[0].chromosome}")
//    println("Fitness: ${population[0].fitness}")
//    println()

//    population
//        .zip(iterate(generation))
//        .distinctBy { it.first.chromosome }
//        .sortedBy { it.second }
//        .let { sorted ->
//            sorted.forEach {
//                println(it.first.chromosome)
//            }
//        }

    println()
    println("Target Chromosome: $TARGET")
    println()

    sortedPopulation
        .distinctBy { it.second.chromosome }
        .forEach { (gen, pop) ->
            println("Gen $gen: ${pop.chromosome}")
        }

}
