// code here

public class Toroidd extends Primitive {

	/** The properties. */
	private final PropertyStorage properties = new PropertyStorage();
	List<Polygon> polys;

	public Toroidd(double innerRadius, double OuterRadius, int numSlices, int facets) {
		if (innerRadius < 0)
			throw new RuntimeException("Inner radious must be positive");
		if (innerRadius >= OuterRadius)
			throw new RuntimeException("Outer radius must be larger than inner radius");
		double crossSecRad = OuterRadius - innerRadius;
		ArrayList<Vertex> vertices = new ArrayList<>();
		double f = facets;

		for (int i = 0; i < facets; i++) {
			double index = i;
			double rad = index / f * 2 * Math.PI;
			double x = Math.sin(rad) * crossSecRad;
			double z = Math.cos(rad) * crossSecRad;
			vertices.add(new Vertex(new Vector3d(0,x,z), new Vector3d(0, 0)));
		}
		Polygon poly = new Polygon(vertices, properties);
		polys = CSG.unionAll(Extrude.revolve(poly, innerRadius, numSlices)).getPolygons();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.mihosoft.vrl.v3d.Primitive#toPolygons()
	 */
	@Override
	public List<Polygon> toPolygons() {
		return polys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.mihosoft.vrl.v3d.Primitive#getProperties()
	 */
	@Override
	public PropertyStorage getProperties() {
		return properties;
	}

}
return new Toroidd(5, 10, 20, 6).toCSG()