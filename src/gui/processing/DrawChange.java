package gui.processing;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import gui.general.Context;

/**
 * Classe des listeners pour créer les formes
 */

public class DrawChange implements MouseMotionListener, MouseListener {

	private int refx, refy;
	private ProcessingModel model;
	private Form form;
	private File im;

	public DrawChange(ProcessingModel m) {
		this.model = m;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		refx = e.getX();
		refy = e.getY();
		System.out.println("x" + refx + "y" + refy);
		int indice = 0;
		for (int i = 0; i < model.getListRect().size(); i++) {

			Form f = model.getListRect().get(i);
			int originXForm = f.getTab()[0];
			int originYForm = f.getTab()[1];
			int endXForm = originXForm + f.getTab()[2];
			int endYForm = originYForm + f.getTab()[3];
			if ((e.getX() > originXForm && e.getX() < endXForm) && (e.getY() > originYForm && e.getY() < endYForm)) {
				form = f;
				if (form.getTypeCommande() == 'i') {
					im = form.getImageA();
				} else
					im = null;
				//System.out.println("Nouveau modele selectionne : " + form.toString());
			}

		}

		// 0 => Marge gauche
		// 1 => Marge haut
		// 2 => Largeur
		// 3 => Longueur
		int[] tab = form.getTab();

		int x = e.getX();
		int y = e.getY();

		if (x > tab[0] - 3 && x < tab[0] + 3 && y > tab[1] && e.getY() < tab[0] + tab[3]) {
			System.out.println("click gauche");
		}

		// 0 < X < 0+2
		// 1 < Y < 1
		if (tab[0] < x && x < tab[0] + tab[2] && tab[1] - 3 < y && y < tab[1] + 3) {
			System.out.println("click haut");
		}

		// 0+2 < X < 0+2
		// 1 < Y < 1+3
		if (tab[0] + tab[2] - 3 < x && x < tab[0] + tab[2] + 3 && tab[1] < y && y < tab[1] + tab[3]) {
			System.out.println("click droit");
		}

		// 0 < x < 0+2
		// 1+3 < y < 1+3
		if (tab[0] < x && x < tab[0] + tab[2] && tab[1] + tab[3] - 3 < y && y < tab[1] + tab[3] + 3) {
			System.out.println("click bas");
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		model.sendChanges();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (((ProcessingModel) Context.$M).isfUp() || ((ProcessingModel) Context.$M).iscropUp()) {
			if (((ProcessingModel) Context.$M).iscropUp() && refx != 0)
				((ProcessingModel) Context.$M).addForm(refx, refy, (e.getX() - refx), (e.getY() - refy), 'c', null);

			else if (((ProcessingModel) Context.$M).isfUp() && refx != 0)
				((ProcessingModel) Context.$M).addForm(refx, refy, (e.getX() - refx), (e.getY() - refy), 'f', null);
		}
		if (!(((ProcessingModel) Context.$M).isfUp() || ((ProcessingModel) Context.$M).iscropUp())) {
			if (form != null) {
				if (im != null) {
					model.addForm(e.getX() - (form.getTab()[2] / 2), e.getY() - (form.getTab()[3] / 2),
							form.getTab()[2], form.getTab()[3], 'i', im);
				} else {
					model.addForm(e.getX() - (form.getTab()[2] / 2), e.getY() - (form.getTab()[3] / 2),
							form.getTab()[2], form.getTab()[3], form.getTypeCommande(), null);
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

}
