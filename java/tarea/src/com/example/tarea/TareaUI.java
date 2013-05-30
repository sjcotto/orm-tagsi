package com.example.tarea;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.example.tarea.interfaces.Api;
import com.example.tarea.interfaces.Factory;
import com.example.tarea.model.Curso;
import com.example.tarea.model.Estudiante;
import com.example.tarea.model.Inscripcione;
import com.example.tarea.model.InscripcionePK;
import com.example.tarea.model.Persona;
import com.example.tarea.model.Profesor;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.Runo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
@Theme("a")
public class TareaUI extends UI {

	private Persona person;
	private Estudiante estudiante;
	private OptionGroup op;
	protected Api api;
	private Curso curso;
	private Table tableCursos;

	@Override
	protected void init(VaadinRequest request) {

		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		Label label = new Label(
				"Laboratorio 1 - PoC Java Hibernate - Tagsi 2013 Grupo A",
				Label.CONTENT_XHTML);
		Label label2 = new Label("Caso de estudio sobre ORMs - Hibernate",
				Label.CONTENT_XHTML);

		op = new OptionGroup("Acceso a datos");
		op.addItem("Hibernate");
		op.addItem("Sql Nativo");

		op.setValue("Sql Nativo");

		HorizontalLayout h = new HorizontalLayout();
		VerticalLayout ver = new VerticalLayout();
		
		ver.addComponent(label);
		ver.addComponent(label2);

		h.addComponent(ver);
		h.addComponent(op);
		
		h.setExpandRatio(ver, 1);
		
		label.setStyleName(Runo.LABEL_H1);
		label2.setStyleName(Runo.LABEL_H2);

		layout.addComponent(h);

		initTabs(layout);

	}

	private void initTabs(VerticalLayout layout) {

		TabSheet tab = new TabSheet();

		VerticalLayout v1 = new VerticalLayout();
		tab.addTab(v1, "Alta Estudiante");
		setAltaEstudianteLayout(v1);

		VerticalLayout v2 = new VerticalLayout();
		tab.addTab(v2, "Buscar Estudiante");
		setBuscarEstudiantesLayout(v2);

		VerticalLayout v3 = new VerticalLayout();
		tab.addTab(v3, "Alta Curso");
		setAltaCursoLayout(v3);

		VerticalLayout v4 = new VerticalLayout();
		tab.addTab(v4, "Cursos");
		setBuscarCursoLayout(v4);

		layout.addComponent(tab);

	}

	private void setBuscarCursoLayout(VerticalLayout v4) {

		v4.setMargin(true);
		v4.setSpacing(true);

		Button btnRepfres = new Button("Actualizar");
		btnRepfres.setIcon(new ThemeResource("refresh.png"));

		btnRepfres.setStyleName(Runo.BUTTON_LINK);

		final BeanContainer<Integer, Curso> beans = new BeanContainer<Integer, Curso>(
				Curso.class);
		tableCursos = new Table("Cursos", beans);

		btnRepfres.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				updateCursosTable(beans);
			}
		});

		updateCursosTable(beans);

		v4.addComponent(btnRepfres);
		v4.addComponent(tableCursos);

		tableCursos.setWidth("250px");
		tableCursos.setHeight("350px");

	}

	private void updateCursosTable(BeanContainer<Integer, Curso> beans) {

		tableCursos.removeAllItems();
		tableCursos.setVisibleColumns(new String[] { "codigo", "nombre",
				"creditos" });

		if (op.getValue().equals("Hibernate")) {
			api = Factory.getInstance().setOrm(true).getController();
		} else {
			api = Factory.getInstance().setOrm(false).getController();
		}

		List<Curso> list = null;
		try {
			list = api.obtenerCursos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		beans.setBeanIdProperty("codigo");
		beans.addAll(list);
		beans.setBeanIdProperty("codigo");
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
		table.setVisibleColumns(new String[] { "id", "fechaIngreso",
				"matricula", "persona" });
		table.setSelectable(true);

		final HorizontalLayout h = new HorizontalLayout();
		h.setMargin(true);
		h.setSpacing(true);

		h.addComponent(table);

		table.setWidth("400px");
		table.setHeight("250px");

		final VerticalLayout ver = new VerticalLayout();
		final Table table_insc = new Table("Inscripciones");
		ver.addComponent(table_insc);
		h.addComponent(ver);
		table_insc.setVisible(false);

		table_insc.setWidth("400px");
		table_insc.setHeight("250px");

		table_insc.addContainerProperty("fecha", Date.class, null);
		table_insc.addContainerProperty("codigo", Integer.class, 0);
		table_insc.addContainerProperty("creditos", String.class, "");
		table_insc.addContainerProperty("nombre", String.class, "");

		btn.addClickListener(new ClickListener() {

			private Api api;

			@Override
			public void buttonClick(ClickEvent event) {
				// buscamos y traemos a todas los estudiantes que tengan ese
				// apellido

				try {

					table.removeAllItems();

					if (op.getValue().equals("Hibernate")) {
						api = Factory.getInstance().setOrm(true)
								.getController();
					} else {
						api = Factory.getInstance().setOrm(false)
								.getController();
					}

					List<Estudiante> list = api.buscarPorApellido(search
							.getValue());

					System.out.println(list);

					BeanContainer<String, Estudiante> beans = new BeanContainer<String, Estudiante>(
							Estudiante.class);

					beans.setBeanIdProperty("id");
					beans.addAll(list);
					beans.setBeanIdProperty("id");

					table.setContainerDataSource(beans);
					table.setVisibleColumns(new String[] { "id",
							"fechaIngreso", "matricula", "persona" });
					search.setValue("");
				} catch (Exception e) {
					e.printStackTrace();
					Notification.show(e.getMessage());
				}

			}
		});

		v2.addComponent(search);
		v2.addComponent(btn);

		Button btn2 = new Button("Inscripciones");
		btn2.addClickListener(new ClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {

				if (table.getValue() == null) {
					Notification
							.show("Debe seleccionar un estudiante de la tabla");
					return;
				}
				if (op.getValue().equals("Hibernate")) {
					api = Factory.getInstance().setOrm(true).getController();
				} else {
					api = Factory.getInstance().setOrm(false).getController();
				}

				ver.removeAllComponents();

				updateTable();

				ver.addComponent(table_insc);

				FormLayout form = new FormLayout();
				final DateField date = new DateField("Fecha Inscripcion");
				final ComboBox combo = new ComboBox();
				combo.setInputPrompt("Seleccione el curso");
				List<Curso> list2 = null;
				try {
					list2 = api.obtenerCursos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (Curso c : list2) {
					combo.addItem(c);
					combo.setItemCaption(c,
							c.getCodigo() + " - " + c.getNombre());
				}

				Button nueva = new Button("Guardar");
				nueva.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {

						try {
							Inscripcione ins = new Inscripcione();
							ins.setCurso((Curso) combo.getValue());
							ins.setFecha(date.getValue());

							InscripcionePK pk = new InscripcionePK();
							pk.setCodigo(((Curso) combo.getValue()).getCodigo());
							pk.setEstudiante((Integer) table.getValue());
							ins.setId(pk);
							api.inscribirEstudianteCurso(
									(Integer) table.getValue(), ins);

						} catch (Exception e) {
							e.printStackTrace();
							Notification.show(e.getCause().getCause()
									.getMessage());
						}

						updateTable();
					}
				});
				form.addComponent(date);
				form.addComponent(combo);
				form.addComponent(nueva);
				ver.addComponent(form);
			}

			@SuppressWarnings("unchecked")
			private void updateTable() {
				List<Inscripcione> list;
				try {
					list = api.obtenerCursosPorEstudiante((Integer) table
							.getValue());
					table_insc.removeAllItems();
					for (Inscripcione ins : list) {
						table_insc.addItem(ins.getId());
						table_insc.getContainerProperty(ins.getId(), "fecha")
								.setValue(ins.getFecha());
						table_insc.getContainerProperty(ins.getId(), "nombre")
								.setValue(ins.getCurso().getNombre());
						table_insc
								.getContainerProperty(ins.getId(), "creditos")
								.setValue(ins.getCurso().getCreditos());
						table_insc.getContainerProperty(ins.getId(), "codigo")
								.setValue(ins.getCurso().getCodigo());
					}
					table_insc.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		v2.addComponent(btn2);
		v2.addComponent(h);
	}

	@SuppressWarnings("deprecation")
	private void setAltaEstudianteLayout(final VerticalLayout v1) {

		v1.removeAllComponents();

		person = new Persona();
		BeanItem<Persona> item = new BeanItem<Persona>(person);
		final Form f1 = new Form();
		f1.setCaption("Datos Basicos (Persona)");
		f1.setFormFieldFactory(createFieldFactory());
		f1.setItemDataSource(item);
		f1.setVisibleItemProperties(new String[] { "nombre", "apellido",
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

					if (op.getValue().equals("Hibernate")) {
						api = Factory.getInstance().setOrm(true)
								.getController();
					} else {
						api = Factory.getInstance().setOrm(false)
								.getController();
					}

					api.registrarEstudiante(estudiante);
					Notification.show("Estudiante guardado");
					setAltaEstudianteLayout(v1);
				} catch (Exception e) {
					e.printStackTrace();
					Notification.show(e.getCause().getCause().getMessage());
				}

			}
		});

		form.addComponent(save);
		v1.addComponent(form);

	}

	@SuppressWarnings("deprecation")
	private void setAltaCursoLayout(final VerticalLayout v1) {

		v1.removeAllComponents();

		curso = new Curso();
		BeanItem<Curso> item = new BeanItem<Curso>(curso);
		final Form f1 = new Form();

		f1.setFormFieldFactory(createFieldFactory());
		f1.setItemDataSource(item);
		f1.setVisibleItemProperties(new String[] { "nombre", "creditos",
				"profesor" });

		FormLayout form = new FormLayout();
		form.addComponent(f1);

		Button save = new Button("Guardar");
		save.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					f1.commit();

					Api api = Factory.getInstance().setOrm(true)
							.getController();
					api.registrarCurso(curso);
					Notification.show("Curso guardado");
					setAltaCursoLayout(v1);
				} catch (Exception e) {
					e.printStackTrace();
					Notification.show(e.getMessage());
				}

			}
		});
		form.addComponent(save);
		v1.addComponent(form);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private FormFieldFactory createFieldFactory() {
		return new DefaultFieldFactory() {

			@SuppressWarnings("rawtypes")
			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				Field f = super.createField(item, propertyId, uiContext);

				if (f instanceof TextField) {
					((TextField) f).setNullRepresentation("");
				}

				if (propertyId.equals("profesor")) {
					ComboBox combo = new ComboBox("Profesor Responsable");
					if (op.getValue().equals("Hibernate")) {
						api = Factory.getInstance().setOrm(true)
								.getController();
					} else {
						api = Factory.getInstance().setOrm(false)
								.getController();
					}
					List<Profesor> list = null;
					try {
						list = api.obtenerProfesores();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (Profesor p : list) {
						combo.addItem(p);
						combo.setItemCaption(p, p.getPersona().getNombre());
					}
					return combo;

				}

				return f;
			}
		};
	}

}