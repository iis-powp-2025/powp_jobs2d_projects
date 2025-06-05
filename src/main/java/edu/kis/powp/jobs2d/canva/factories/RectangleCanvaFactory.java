package edu.kis.powp.jobs2d.canva.factories;

import edu.kis.powp.jobs2d.canva.shapes.RectangleCanva;

/**
 * A factory class for creating predefined {@link RectangleCanva} instances
 * with standard paper sizes in both vertical and horizontal orientations.
 */
public class RectangleCanvaFactory {

    /**
     * Returns a vertical A5 size canvas (148mm x 210mm).
     *
     * @return a {@link RectangleCanva} with dimensions 148mm (width) x 210mm (height)
     */
    public static RectangleCanva getVerticalA5Canva() {
        return new RectangleCanva(148, 210);
    }

    /**
     * Returns a horizontal A5 size canvas (210mm x 148mm).
     *
     * @return a {@link RectangleCanva} with dimensions 210mm (width) x 148mm (height)
     */
    public static RectangleCanva getHorizontalA5Canva() {
        return new RectangleCanva(210, 148);
    }

    /**
     * Returns a vertical A4 size canvas (210mm x 297mm).
     *
     * @return a {@link RectangleCanva} with dimensions 210mm (width) x 297mm (height)
     */
    public static RectangleCanva getVerticalA4Canva() {
        return new RectangleCanva(210, 297);
    }

    /**
     * Returns a horizontal A4 size canvas (297mm x 210mm).
     *
     * @return a {@link RectangleCanva} with dimensions 297mm (width) x 210mm (height)
     */
    public static RectangleCanva getHorizontalA4Canva() {
        return new RectangleCanva(297, 210);
    }

    /**
     * Returns a vertical A3 size canvas (297mm x 420mm).
     *
     * @return a {@link RectangleCanva} with dimensions 297mm (width) x 420mm (height)
     */
    public static RectangleCanva getVerticalA3Canva() {
        return new RectangleCanva(297, 420);
    }

    /**
     * Returns a horizontal A3 size canvas (420mm x 297mm).
     *
     * @return a {@link RectangleCanva} with dimensions 420mm (width) x 297mm (height)
     */
    public static RectangleCanva getHorizontalA3Canva() {
        return new RectangleCanva(420, 297);
    }

    /**
     * Returns a US Letter size canvas (216mm x 279mm).
     *
     * @return a {@link RectangleCanva} with dimensions 216mm (width) x 279mm (height)
     */
    public static RectangleCanva getLetterCanva() {
        return new RectangleCanva(216, 279);
    }
}

