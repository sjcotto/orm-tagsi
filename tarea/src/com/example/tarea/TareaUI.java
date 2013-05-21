package com.example.tarea;

import java.util.ArrayList;
import java.util.List;

import com.example.tarea.interfaces.Api;
import com.example.tarea.interfaces.Factory;
import com.example.tarea.model.Estudiante;
import com.example.tarea.model.Persona;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class TareaUI extends UI {

	private Persona person;
	private Estudiante estudiante;

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		TabSheet tab = new TabSheet();

		VerticalLayout v1 = new VerticalLayout();
		tab.addTab(v1, "Alta Estudiante");
		setEstudiantesLayout(v1);

		VerticalLayout v2 = new VerticalLayout();
		tab.addTab(v2, "Buscar Estudiante");
		setBuscarEstudiantesLayout(v2);

		VerticalLayout v3 = new VerticalLayout();
		tab.addTab(v3, "Cursos");

		VerticalLayout v4 = new VerticalLayout();
		tab.addTab(v4, "Inscripciones");

		layout.addComponent(tab);

	}

	private void setBuscarEstudiantesLayout(VerticalLayout v2) {

		v2.setMargin(true);
		v2.setSpacing(true);

		final TextField search = new TextField();
		search.setInputPrompt("Ingrese el apellido del estudiante");

		search.setWidth("200px");
		Button btn = new Button("Buscar");

		BeanContainer<String, Estudiante> beans = new BeanContainer<String, Estudiante>(
				Estudiante.class);
		final Table table = new Table("Estudiantes", beans);

		btn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// buscamos y traemos a todas los estudiantes que tengan ese
				// apellido

				try {

					table.removeAllItems();

					Api api = Factory.getInstance().setOrm(true)
							.getController();
					List<Estudiante> list = api.buscarPorApellido(search
							.getValue());
					BeanContainer<String, Estudiante> beans = new BeanContainer<String, Estudiante>(
							Estudiante.class);

					beans.setBeanIdProperty("id");
					beans.addAll(list);
					beans.setBeanIdProperty("id");

					table.setContainerDataSource(beans);
					table.setVisibleColumns(new String[]{"id","fechaIngreso","matricula","persona"});
					search.setValue("");
				} catch (Exception e) {
					e.printStackTrace();
					Notification.show(e.getMessage());
				}

			}
		});

		v2.addComponent(search);
		v2.addComponent(btn);
		v2.addComponent(table);
	}

	@SuppressWarnings("deprecation")
	private void setEstudiantesLayout(final VerticalLayout v1) {

		v1.removeAllComponents();

		person = new Persona();
		BeanItem<Persona> item = new BeanItem<Persona>(person);
		final Form f1 = new Form();
		f1.setCaption("Datos Basicos (Persona)");
		f1.setFormFieldFactory(createFieldFactory());
		f1.setItemDataSource(item);
		f1.setVisibleItemProperties(new String[] { "id", "nombre", "apellido",
				"fechaNacimiento", "telefono" });

		estudiante = new Estudiante();
		BeanItem<Estudiante> item2 = new BeanItem<Estudiante>(estudiante);

		final Form f2 = new Form();
		f2.setCaption("Otros Datos (Estudiante)");
		f2.setFormFieldFactory(createFieldFactory());
		f2.setItemDataSource(item2);
		f2.setVisibleItemProperties(new String[] { "fechaIngreso", "matricula" });

		FormLayout form = new FormLayout();
		form.addComponent(f1);
		form.addComponent(f2);

		Button save = new Button("Guardar");
		save.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					f1.commit();
					f2.commit();
					estudiante.setPersona(person);
					estudiante.setId(person.getId());

					Api api = Factory.getInstance().setOrm(true)
							.getController();
					api.registrarEstudiante(estudiante);
					Notification.show("Estudiante guardado");
					setEstudiantesLayout(v1);
				} catch (Exception e) {
					e.printStackTrace();
					Notification.show(e.getMessage());
				}

			}
		});
		form.addComponent(save);
		v1.addComponent(form);

	}

	@SuppressWarnings({ "serial", "unused" })
	private FormFieldFactory createFieldFactory() {
		return new DefaultFieldFactory() {

			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				Field f = super.createField(item, propertyId, uiContext);

				if (f instanceof TextField) {
					((TextField) f).setNullRepresentation("");
				}

				return f;
			}
		};
	}

}