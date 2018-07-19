package com.yellowsquash.automata

object Neighbor extends Enumeration {
  type Neighbor = Value
  val N, E, S, W, NE, SE, SW, NW, NN, EE, SS, WW = Value
}

object Neighborhood extends Enumeration {
	import com.yellowsquash.automata.Neighbor.Neighbor
	type Neighborhood = Value
	val VON_NEUMANN, MOORE = Value

	def getNeighbors(n: Neighborhood): List[Neighbor] = n match {
		case VON_NEUMANN 	=> List(
									Neighbor.NN
									, Neighbor.N
									, Neighbor.EE
									, Neighbor.E
									, Neighbor.SS
									, Neighbor.S
									, Neighbor.WW
									, Neighbor.W
								)
		case _ 				=> 	List(
									Neighbor.N
									, Neighbor.NE
									, Neighbor.E
									, Neighbor.SE
									, Neighbor.S
									, Neighbor.SW
									, Neighbor.W
									, Neighbor.NW
								)
	} 
}