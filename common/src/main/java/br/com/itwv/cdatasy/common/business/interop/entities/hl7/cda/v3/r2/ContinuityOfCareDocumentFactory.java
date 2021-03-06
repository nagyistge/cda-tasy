package br.com.itwv.cdatasy.common.business.interop.entities.hl7.cda.v3.r2;

import br.com.itwv.cdatasy.base.encoding.streams.FastByteArrayOutputStream;
import br.com.itwv.cdatasy.common.business.interop.entities.hl7.cda.v3.r2.helpers.ClinicalMappingEntryRelationships;
import br.com.itwv.cdatasy.common.business.interop.mappings.types.CDADataTypesFactory;
import br.com.itwv.cdatasy.common.business.resources.Resources;
import org.eclipse.emf.common.util.Diagnostic;
import org.openhealthtools.mdht.uml.cda.*;
import org.openhealthtools.mdht.uml.cda.ccd.*;
import org.openhealthtools.mdht.uml.cda.ccd.Product;
import org.openhealthtools.mdht.uml.cda.util.BasicValidationHandler;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.vocab.*;

import static br.com.itwv.cdatasy.common.business.interop.mappings.interfaces.IClinicalMapping.x_DocEntryStatusCode;

import java.io.InputStream;
import java.util.UUID;

public class ContinuityOfCareDocumentFactory implements IClinicalDocumentFactory {

    private static ClinicalDocument clinicalDocumentInstance;
    private static ContinuityOfCareDocumentFactory instance = new ContinuityOfCareDocumentFactory();

    protected ContinuityOfCareDocumentFactory() {
    }

    public static ContinuityOfCareDocumentFactory getInstance() {
        return instance;
    }

    private static void loadClinicalDocumentByFile(boolean validateClinicalDocument) {
        try {
            ContinuityOfCareDocumentFactory.clinicalDocumentInstance = CDAUtil.load((InputStream) Resources.class
                    .getResourceAsStream("/samples/SampleCCDDocument.xml"));
            if (validateClinicalDocument)
                ContinuityOfCareDocumentFactory.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadClinicalDocumentDefault(boolean instanceSections) {
        try {
            ContinuityOfCareDocumentFactory.clinicalDocumentInstance = CDAFactory.eINSTANCE.createClinicalDocument();
            if (instanceSections) {
                ContinuityOfCareDocumentFactory.clinicalDocumentInstance.addSection(ContinuityOfCareDocumentFactory
                        .instanceSection(x_EObjectTypes.ALLERGIES));
                ContinuityOfCareDocumentFactory.clinicalDocumentInstance.addSection(ContinuityOfCareDocumentFactory
                        .instanceSection(x_EObjectTypes.MEDICATIONS));
                ContinuityOfCareDocumentFactory.clinicalDocumentInstance.addSection(ContinuityOfCareDocumentFactory
                        .instanceSection(x_EObjectTypes.IMMUNIZATIONS));
                ContinuityOfCareDocumentFactory.clinicalDocumentInstance.addSection(ContinuityOfCareDocumentFactory
                        .instanceSection(x_EObjectTypes.PROBLEMS));
                ContinuityOfCareDocumentFactory.clinicalDocumentInstance.addSection(ContinuityOfCareDocumentFactory
                        .instanceSection(x_EObjectTypes.PROCEDURES));
                ContinuityOfCareDocumentFactory.clinicalDocumentInstance.addSection(ContinuityOfCareDocumentFactory
                        .instanceSection(x_EObjectTypes.MEDICALEQUIPMENT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadClinicalDocumentByInputStream(InputStream clinicalDocument, boolean validateClinicalDocument) {
        try {

            CCDPackage.eINSTANCE.eClass();
            ContinuityOfCareDocumentFactory.clinicalDocumentInstance = CDAUtil.load(clinicalDocument);
            if (validateClinicalDocument)
                ContinuityOfCareDocumentFactory.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ClinicalDocument getClinicalDocumentInstance() {
        return ContinuityOfCareDocumentFactory.clinicalDocumentInstance;
    }

    public static void setClinicalDocumentInstance(ClinicalDocument clinicalDocumentInstance) {
        ContinuityOfCareDocumentFactory.clinicalDocumentInstance = clinicalDocumentInstance;
    }

    private static boolean validate() throws Exception {
        return CDAUtil.validate(ContinuityOfCareDocumentFactory.clinicalDocumentInstance, new BasicValidationHandler() {
            @Override
            public void handleError(Diagnostic diagnostic) {
                System.out.println("ERROR: " + diagnostic.getMessage());
            }

            @Override
            public void handleWarning(Diagnostic diagnostic) {
                System.out.println("WARNING: " + diagnostic.getMessage());
            }

            @Override
            public void handleInfo(Diagnostic diagnostic) {
                System.out.println("INFO: " + diagnostic.getMessage());
            }
        });
    }

    public static String getString() {
        try {
            FastByteArrayOutputStream writer = new FastByteArrayOutputStream();
            CDAUtil.save(ContinuityOfCareDocumentFactory.clinicalDocumentInstance, writer);
            String ret = writer.toString();
            writer.close();
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] getBytes() {
        try {
            FastByteArrayOutputStream writer = new FastByteArrayOutputStream();
            CDAUtil.save(ContinuityOfCareDocumentFactory.clinicalDocumentInstance, writer);
            byte[] ret = writer.toString().getBytes();
            writer.close();
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public void createClinicalDocumentFactory(x_FactoryLoadTypes factoryLoadType, Object obj) {
        this.createClinicalDocumentFactory(factoryLoadType, obj, false, true);
    }

    public void createClinicalDocumentFactory(x_FactoryLoadTypes factoryLoadType, Object obj, boolean validateClinicalDocument,
                                              boolean instanceSections) {

        ContinuityOfCareDocumentFactory.clinicalDocumentInstance = null;
        switch (factoryLoadType) {
            case FILE:
                ContinuityOfCareDocumentFactory.loadClinicalDocumentByFile(validateClinicalDocument);
                break;
            case INPUTSTREAM:
                ContinuityOfCareDocumentFactory.loadClinicalDocumentByInputStream((InputStream) obj, validateClinicalDocument);
                break;
            case DEFAULT:
                ContinuityOfCareDocumentFactory.loadClinicalDocumentDefault(instanceSections);
            default:
                break;
        }
    }

    private static Section instanceSection(x_EObjectTypes x) throws Exception {
        switch (x) {
            case ALLERGIES:
                return ContinuityOfCareDocumentFactory.instanceAlertsSection();
            case IMMUNIZATIONS:
                return ContinuityOfCareDocumentFactory.instanceImmunizationsSection();
            case MEDICATIONS:
                return ContinuityOfCareDocumentFactory.instanceMedicationsSection();
            case PROBLEMS:
                return ContinuityOfCareDocumentFactory.instanceProblemsSection();
            case PROCEDURES:
                return ContinuityOfCareDocumentFactory.instanceProceduresSection();
            case MEDICALEQUIPMENT:
                return ContinuityOfCareDocumentFactory.instanceMedicalEquipmentSection();
            default:
                return null;
        }
    }

    private static MedicationsSection instanceMedicationsSection() throws Exception {

        MedicationsSection medicationsSection = CCDFactory.eINSTANCE.createMedicationsSection().init();
        medicationsSection.setTitle(DatatypesFactory.eINSTANCE.createST("Medicamentos Usados"));
        medicationsSection.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.12559.11.10.1.3.1.2.3", null));
        medicationsSection.setMoodCode(ActMood.EVN);
        medicationsSection.createStrucDocText("<content ID=\"nomedicationdescription\">Não existem medicamentos.</content>");
        medicationsSection.addSubstanceAdministration(ContinuityOfCareDocumentFactory.createMedicationsSectionEntry(null, true));

        return medicationsSection;
    }

    private static ImmunizationsSection instanceImmunizationsSection() throws Exception {

        ImmunizationsSection immunizationsSection = CCDFactory.eINSTANCE.createImmunizationsSection().init();
        immunizationsSection.setTitle(DatatypesFactory.eINSTANCE.createST("Historial de Vacinação"));
        immunizationsSection.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.3.23", null));
        immunizationsSection.setMoodCode(ActMood.EVN);
        immunizationsSection.createStrucDocText("<content ID=\"noimmunizationsdescription\">Não existem vacinas.</content>");
        immunizationsSection.addSubstanceAdministration(ContinuityOfCareDocumentFactory.createImmunizationsSectionEntry(null, true));

        return immunizationsSection;
    }

    private static AlertsSection instanceAlertsSection() throws Exception {

        AlertsSection alertsSection = CCDFactory.eINSTANCE.createAlertsSection().init();
        alertsSection.setTitle(DatatypesFactory.eINSTANCE.createST("Alergias, reacções adversas, alertas"));
        alertsSection.setMoodCode(ActMood.EVN);
        alertsSection.createStrucDocText("<content ID=\"noallergy\">Não existem reacções adversas.</content>");
        alertsSection.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.3.13", null));
        alertsSection.addAct(ContinuityOfCareDocumentFactory.createAlertsSectionEntry(null, true));
        return alertsSection;
    }

    private static ProblemSection instanceProblemsSection() throws Exception {

        ProblemSection problemSection = CCDFactory.eINSTANCE.createProblemSection().init();
        problemSection.setTitle(DatatypesFactory.eINSTANCE.createST("Historial de Doenças"));
        problemSection.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.3.6", null));
        problemSection.setMoodCode(ActMood.EVN);
        problemSection.createStrucDocText("<content ID=\"actnoproblem\">Não existem problemas activos.</content>");
        problemSection.addAct(ContinuityOfCareDocumentFactory.createProblemsSectionEntry(null, true));

        return problemSection;
    }

    private static ProceduresSection instanceProceduresSection() throws Exception {

        ProceduresSection proceduresSection = CCDFactory.eINSTANCE.createProceduresSection().init();
        proceduresSection.setTitle(DatatypesFactory.eINSTANCE.createST("Intervenções Cirúrgicas"));
        proceduresSection.setMoodCode(ActMood.EVN);
        proceduresSection.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.3.11", null));
        proceduresSection.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.3.12", null));
        proceduresSection.createStrucDocText("<content ID=\"nosurgeries\">Sem cirurgias conhecidas.</content>");
        proceduresSection.addProcedure(ContinuityOfCareDocumentFactory.createProceduresSectionEntry(null, true));

        return proceduresSection;
    }

    private static MedicalEquipmentSection instanceMedicalEquipmentSection() throws Exception {

        MedicalEquipmentSection medicalEquipmentSection = CCDFactory.eINSTANCE.createMedicalEquipmentSection().init();
        medicalEquipmentSection.setTitle(DatatypesFactory.eINSTANCE.createST("Dispositivos Médicos e Implantes"));
        medicalEquipmentSection.getTemplateIds().add(
                CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.1.5.3.5", null));
        medicalEquipmentSection.getTemplateIds().add(
                CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.12559.11.10.1.3.1.2.4", "epSOS"));
        medicalEquipmentSection.setMoodCode(ActMood.EVN);
        medicalEquipmentSection.createStrucDocText("<content ID=\"nomedicalequipment\">Não existem dispositivos médicos.</content>");
        medicalEquipmentSection.addSupply(ContinuityOfCareDocumentFactory.createMedicalEquipmentSectionEntry(null, true));

        return medicalEquipmentSection;
    }

    public static String clinicalDocumentToString(ClinicalDocument doc) {

        try {
            FastByteArrayOutputStream writer = new FastByteArrayOutputStream();
            CDAUtil.save(doc, writer);
            String docString = writer.toString();
            writer.close();
            return docString;
        } catch (Exception e) {
            return null;
        }
    }

    public static ProblemAct createProblemsSectionEntry(ProblemAct problemAct, boolean emptyEntry) throws Exception {

        if (problemAct == null) {
            problemAct = CCDFactory.eINSTANCE.createProblemAct().init();
            problemAct.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.5.1", null));
            problemAct.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.5.2", null));
            problemAct.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("held", x_DocEntryStatusCode.HELD));
            problemAct.setCode(CDADataTypesFactory.getInstance().createBaseCodeCD("NA", NullFlavor.NA));
            problemAct.setClassCode(x_ActClassDocumentEntryAct.ACT);
            problemAct.setMoodCode(x_DocumentActMood.EVN);
        }

        ProblemObservation problemObservation = CCDFactory.eINSTANCE.createProblemObservation().init();
        problemAct.addObservation(problemObservation);
        problemObservation.setNegationInd(false);
        problemObservation.setText(CDADataTypesFactory.getInstance().createBaseED(null, "problem"));
        problemObservation.setEffectiveTime(CDADataTypesFactory.getInstance().createBaseEffectiveTimeIVL_TS(null, null));
        problemObservation.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.5", null));
        problemObservation.setCode(CDADataTypesFactory.getInstance().createBaseCodeCD("57041-6", "2.16.840.1.113883.6.1", "LOINC",
                "Histórico do utente e diagnósticos", null));
        problemObservation.getIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, null, null));
        problemObservation.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("completed", x_DocEntryStatusCode.COMPLETED));

        if (emptyEntry) {
            problemAct.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("completed", x_DocEntryStatusCode.COMPLETED));
            problemAct.getIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, UUID.randomUUID().toString(), null));
            problemAct.setEffectiveTime(CDADataTypesFactory.getInstance().createBaseEffectiveTimeIVL_TS(null, null));
            problemObservation.setCode(CDADataTypesFactory.getInstance().createBaseCodeCD(null));
            problemObservation.setText(CDADataTypesFactory.getInstance().createBaseED(null, "actnoproblem"));
            // TODO: SNOMED
            problemObservation.getValues().add(
                    CDADataTypesFactory.getInstance().createBaseCodeCD("160245001", "2.16.840.1.113883.6.96", "SNOMED-CT",
                            "Não existem problemas activos", CDADataTypesFactory.getInstance().createBaseED(null, "actnoproblem")));
            ClinicalMappingEntryRelationships.getInstance().defineProblemActEntryRelationships(
                    ContinuityOfCareDocumentFactory.clinicalDocumentInstance, problemAct);
        }

        return problemAct;
    }

    public static ProcedureActivityProcedure createProceduresSectionEntry(ProcedureActivityProcedure procedureActivityProcedure, boolean emptyEntry)
            throws Exception {

        if (procedureActivityProcedure == null) {
            procedureActivityProcedure = CCDFactory.eINSTANCE.createProcedureActivityProcedure().init();
            procedureActivityProcedure.setClassCode(ActClass.PROC);
            procedureActivityProcedure.setMoodCode(x_DocumentProcedureMood.EVN);
            procedureActivityProcedure.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("held", x_DocEntryStatusCode.HELD));
            procedureActivityProcedure.getTemplateIds().add(
                    CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.19", null));
            procedureActivityProcedure.setCode(CDADataTypesFactory.getInstance().createBaseCodeCD("NA", NullFlavor.NA));
            procedureActivityProcedure.setText(CDADataTypesFactory.getInstance().createBaseED(null, "procedure"));
        }

        ProcedureActivityObservation procedureActivityObservation = CCDFactory.eINSTANCE.createProcedureActivityObservation().init();
        procedureActivityProcedure.addObservation(procedureActivityObservation);
        procedureActivityObservation.getIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, UUID.randomUUID().toString(), null));

        if (emptyEntry) {
            procedureActivityProcedure.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("completed",
                    x_DocEntryStatusCode.COMPLETED));
            procedureActivityProcedure.setEffectiveTime(CDADataTypesFactory.getInstance().createBaseEffectiveTimeIVL_TS(null, null));
            procedureActivityProcedure.setText(CDADataTypesFactory.getInstance().createBaseED(null, "noprocedure"));
            procedureActivityProcedure.getIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, UUID.randomUUID().toString(), null));
        }
        return procedureActivityProcedure;
    }

    public static Supply createMedicalEquipmentSectionEntry(Supply supply, boolean emptyEntry) throws Exception {

        if (supply == null) {
            supply = CDAFactory.eINSTANCE.createSupply();
        }
        Participant2 participant = CDAFactory.eINSTANCE.createParticipant2();
        ParticipantRole participantRole = CDAFactory.eINSTANCE.createParticipantRole();
        Device playingDevice = CDAFactory.eINSTANCE.createDevice();
        supply.setClassCode(ActClassSupply.SPLY);
        supply.setMoodCode(x_DocumentSubstanceMood.EVN);

        if (emptyEntry) {
            supply.setNullFlavor(NullFlavor.NA);
            supply.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.12559.11.10.1.3.1.3.5", null));
            supply.getEffectiveTimes().add(CDADataTypesFactory.getInstance().createBaseSXCM_TS(null, NullFlavor.NA));
            supply.getParticipants().add(participant);
            participant.setParticipantRole(participantRole);
            participantRole.setPlayingDevice(playingDevice);
            participant.setTypeCode(ParticipationType.DEV);
            participantRole.setClassCode(RoleClassRoot.MANU);
            playingDevice.setClassCode(EntityClassDevice.DEV);
            playingDevice.setDeterminerCode(EntityDeterminer.INSTANCE);
            playingDevice.setCode(CDADataTypesFactory.getInstance().createBaseCodeCE(null));
        }
        return supply;
    }

    public static MedicationActivity createMedicationsSectionEntry(MedicationActivity medicationActivity, boolean emptyEntry) throws Exception {

        Material material = CDAFactory.eINSTANCE.createMaterial();

        if (medicationActivity == null) {
            medicationActivity = CCDFactory.eINSTANCE.createMedicationActivity().init();
            Consumable consumable = CDAFactory.eINSTANCE.createConsumable();
            Product product = CCDFactory.eINSTANCE.createProduct().init();
            medicationActivity.setMoodCode(x_DocumentSubstanceMood.EVN);
            medicationActivity.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.7", null));
            medicationActivity.getTemplateIds()
                    .add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.7.1", null));
            medicationActivity.getTemplateIds().add(
                    CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.12559.11.10.1.3.1.3.4", null));
            medicationActivity.setClassCode(ActClass.SBADM);
            medicationActivity.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("new", x_DocEntryStatusCode.NEW));
            medicationActivity.setConsumable(consumable);
            consumable.setManufacturedProduct(product);
            product.setManufacturedMaterial(material);
            product.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.7.2", null));
            material.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.12559.11.10.1.3.1.3.1", null));
            material.setName(CDADataTypesFactory.getInstance().createBaseEN(null));
            material.setCode(CDADataTypesFactory.getInstance().createBaseCodeCE(null, null, null, null,
                    CDADataTypesFactory.getInstance().createBaseED(null, "medicationdescription")));
        }

        if (emptyEntry) {
            // TODO: SNOMED
            medicationActivity.setCode(CDADataTypesFactory.getInstance().createBaseCodeCD("182904002", "2.16.840.1.113883.6.96", "SNOMED-CT",
                    "Tratamento desconhecido", CDADataTypesFactory.getInstance().createBaseED(null, "medicationdescription")));
            medicationActivity.getIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, UUID.randomUUID().toString(), null));
            medicationActivity.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("completed", x_DocEntryStatusCode.COMPLETED));
            medicationActivity.setDoseQuantity(CDADataTypesFactory.getInstance().createBaseDoseQuantityIVL_PQ());
            medicationActivity.setRouteCode(null);
            material.setCode(CDADataTypesFactory.getInstance().createBaseCodeCE(null, null, null, null,
                    CDADataTypesFactory.getInstance().createBaseED(null, "nomedicationdescription")));
            ClinicalMappingEntryRelationships.getInstance().defineMedicationActivityEntryRelationships(
                    ContinuityOfCareDocumentFactory.clinicalDocumentInstance, medicationActivity);
        }
        return medicationActivity;
    }

    public static ProblemAct createAlertsSectionEntry(ProblemAct problemAct, boolean emptyEntry) throws Exception {

        if (problemAct == null) {
            problemAct = CCDFactory.eINSTANCE.createProblemAct().init();
            problemAct.setClassCode(x_ActClassDocumentEntryAct.ACT);
            problemAct.setMoodCode(x_DocumentActMood.EVN);
            problemAct.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.5.1", null));
            problemAct.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.5.3", null));
            problemAct.setCode(CDADataTypesFactory.getInstance().createBaseCodeCD("NA", NullFlavor.NA));
            problemAct.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("new", x_DocEntryStatusCode.NEW));
        }
        AlertObservation alertObservation = CCDFactory.eINSTANCE.createAlertObservation().init();
        problemAct.addObservation(alertObservation);
        problemAct.setEffectiveTime(CDADataTypesFactory.getInstance().createBaseEffectiveTimeIVL_TS(null, null));
        alertObservation.setClassCode(ActClassObservation.OBS);
        alertObservation.setNegationInd(false);
        alertObservation.setMoodCode(x_ActMoodDocumentObservation.EVN);
        alertObservation.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.6", null));
        alertObservation.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.5", null));
        alertObservation.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "2.16.840.1.113883.10.20.1.28", null));
        alertObservation.getIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, UUID.randomUUID().toString(), null));
        alertObservation.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("completed", x_DocEntryStatusCode.COMPLETED));
        alertObservation.setEffectiveTime(CDADataTypesFactory.getInstance().createBaseEffectiveTimeIVL_TS(null, null));
        alertObservation.setText(CDADataTypesFactory.getInstance().createBaseED(null, "allergy"));
        alertObservation.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("completed", x_DocEntryStatusCode.COMPLETED));

        if (emptyEntry) {
            problemAct.getIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, UUID.randomUUID().toString(), null));
            problemAct.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("completed", x_DocEntryStatusCode.COMPLETED));
            alertObservation.setCode(CDADataTypesFactory.getInstance().createBaseCodeCD("NA", NullFlavor.NA));
            alertObservation.setEffectiveTime(CDADataTypesFactory.getInstance().createBaseEffectiveTimeIVL_TS(null, null));
            alertObservation.getValues().add(
                    CDADataTypesFactory.getInstance().createBaseCodeCD("52473-6", "2.16.840.1.113883.6.1", "LOINC", "Observação da Alergia",
                            CDADataTypesFactory.getInstance().createBaseED(null, "noallergy")));
            ClinicalMappingEntryRelationships.getInstance().defineProblemActEntryRelationships(
                    ContinuityOfCareDocumentFactory.clinicalDocumentInstance, problemAct);
        }
        return problemAct;
    }

    public static MedicationActivity createImmunizationsSectionEntry(MedicationActivity medicationActivity, boolean emptyEntry) throws Exception {

        Material material = CDAFactory.eINSTANCE.createMaterial();
        if (medicationActivity == null) {
            medicationActivity = CCDFactory.eINSTANCE.createMedicationActivity().init();
            Consumable consumable = CDAFactory.eINSTANCE.createConsumable();
            Product product = CCDFactory.eINSTANCE.createProduct().init();
            medicationActivity.setMoodCode(x_DocumentSubstanceMood.EVN);
            medicationActivity.setClassCode(ActClass.SBADM);
            medicationActivity.setNegationInd(false);
            medicationActivity.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("new", x_DocEntryStatusCode.NEW));
            medicationActivity.setCode(CDADataTypesFactory.getInstance().createBaseCodeCD("IMMUNIZ", "2.16.840.1.113883.5.4", "ActCode", null, null));
            medicationActivity.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.12", null));
            medicationActivity.setConsumable(consumable);
            consumable.setManufacturedProduct(product);
            product.setManufacturedMaterial(material);
            consumable.setTypeCode(ParticipationType.CSM);
            product.setClassCode(RoleClassManufacturedProduct.MANU);
            product.getTemplateIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, "1.3.6.1.4.1.19376.1.5.3.1.4.7.2", null));
            material.setClassCode(EntityClassManufacturedMaterial.MMAT);
            material.setDeterminerCode(EntityDeterminerDetermined.KIND);
            material.setCode(CDADataTypesFactory.getInstance().createBaseCodeCE(null, null, null, null,
                    CDADataTypesFactory.getInstance().createBaseED(null, "immunizationsdescription")));
        }

        if (emptyEntry) {
            medicationActivity.getIds().add(CDADataTypesFactory.getInstance().createBaseRootII(null, UUID.randomUUID().toString(), null));
            medicationActivity.setStatusCode(CDADataTypesFactory.getInstance().createBaseStatusCodeCS("completed", x_DocEntryStatusCode.COMPLETED));
            medicationActivity.setDoseQuantity(CDADataTypesFactory.getInstance().createBaseDoseQuantityIVL_PQ());
            medicationActivity.setRouteCode(null);
            material.setCode(CDADataTypesFactory.getInstance().createBaseCodeCE(null, null, null, null,
                    CDADataTypesFactory.getInstance().createBaseED(null, "noimmunizationsdescription")));
            ClinicalMappingEntryRelationships.getInstance().defineMedicationActivityEntryRelationships(
                    ContinuityOfCareDocumentFactory.clinicalDocumentInstance, medicationActivity);
        }
        return medicationActivity;
    }
}