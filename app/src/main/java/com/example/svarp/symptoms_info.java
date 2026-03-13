package com.example.svarp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class symptoms_info extends AppCompatActivity {

    private TextView txtSymptoms;
    private TextView txtInfo;
    private boolean isHindi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_info);

        // Read language preference
        SharedPreferences prefs = getSharedPreferences(
                LanguageAdapter.PREFS_NAME, MODE_PRIVATE);
        isHindi = LanguageAdapter.LANG_HINDI.equals(
                prefs.getString(LanguageAdapter.KEY_LANGUAGE, LanguageAdapter.LANG_ENGLISH));

        ImageView btnBack = findViewById(R.id.btnBack);
        txtSymptoms = findViewById(R.id.txtSymptoms);
        txtInfo = findViewById(R.id.txtInfo);

        btnBack.setOnClickListener(v -> finish());

        ArrayList<String> symptoms =
                getIntent().getStringArrayListExtra("selected_symptoms");

        if (symptoms != null && !symptoms.isEmpty()) {

            StringBuilder builder = new StringBuilder();
            for (String s : symptoms) {
                builder.append("• ").append(s).append("\n");
            }

            txtSymptoms.setText(builder.toString());
            txtInfo.setText(generateInfo(symptoms));
        }
    }

    private String generateInfo(ArrayList<String> symptoms) {

        StringBuilder info = new StringBuilder();

        for (String s : symptoms) {
            if (isHindi) {
                switch (s) {

                    case "Fever":
                        info.append("🌡️ बुखार\n")
                                .append("बुखार आपके शरीर की प्राकृतिक रक्षा प्रणाली है — यह तापमान बढ़ाकर वायरस और बैक्टीरिया को कमज़ोर करता है। हल्का बुखार (38.5°C तक) घर पर आराम और पानी पीकर ठीक हो सकता है। अगर बुखार 39.5°C से ज़्यादा हो, 3 दिन से ज़्यादा रहे, या गर्दन अकड़न या चकत्तों के साथ आए — तुरंत डॉक्टर से मिलें।\n\n");
                        break;

                    case "Cough":
                        info.append("🫁 खांसी\n")
                                .append("खांसी आपके वायुमार्ग का बलगम और जलन साफ करने का तरीका है। सूखी खांसी आमतौर पर वायरल इन्फेक्शन या एलर्जी से होती है, जबकि बलगम वाली खांसी छाती के संक्रमण का संकेत हो सकती है। ज़्यादातर खांसी 2 हफ्तों में ठीक हो जाती है। अगर खांसी में खून आए या 3 हफ्ते से ज़्यादा रहे — डॉक्टर से मिलें।\n\n");
                        break;

                    case "Headache":
                        info.append("🧠 सिरदर्द\n")
                                .append("सिरदर्द बहुत आम है और ज़्यादातर हानिरहित होता है। तनाव, खराब मुद्रा, या स्क्रीन टाइम से तनाव वाला सिरदर्द होता है। पानी की कमी भी एक आम कारण है — पानी पीने से जल्दी राहत मिल सकती है। माइग्रेन ज़्यादा तीव्र होता है, अक्सर एक तरफ, और जी मिचलाने या रोशनी से तकलीफ के साथ आता है। अचानक बहुत तेज़ सिरदर्द जो पहले कभी नहीं हुआ — तुरंत डॉक्टर के पास जाएं।\n\n");
                        break;

                    case "Fatigue":
                        info.append("😴 थकान\n")
                                .append("थकान सिर्फ नींद की कमी नहीं है — यह लगातार ऊर्जा की कमी है जो आराम करने के बाद भी पूरी तरह नहीं जाती। खराब नींद, एनीमिया, थायराइड की समस्या, वायरल इन्फेक्शन, तनाव — ये सभी कारण हो सकते हैं। बीमारी के दौरान थकान सामान्य है। अगर 2 हफ्ते से ज़्यादा बिना कारण के थकान हो — एक साधारण ब्लड टेस्ट से कारण पता चल सकता है।\n\n");
                        break;

                    case "Sore Throat":
                        info.append("🗣️ गले में दर्द\n")
                                .append("गले में दर्द ज़्यादातर वायरल इन्फेक्शन जैसे सर्दी से होता है — इसमें एंटीबायोटिक काम नहीं करती। यह 5–7 दिनों में खुद ठीक हो जाता है। गर्म नमक के पानी से गरारे, शहद वाली चाय, और पानी पीने से जल्दी ठीक होता है। अगर गला बहुत सूजा हो, सफेद धब्बे हों, या निगलने में बहुत तकलीफ हो — यह बैक्टीरियल इन्फेक्शन हो सकता है, डॉक्टर से मिलें।\n\n");
                        break;

                    case "Vomiting":
                        info.append("🤢 उल्टी\n")
                                .append("उल्टी आमतौर पर पेट के वायरस, खाने से जहर, या मोशन सिकनेस से होती है और 24 घंटे में ठीक हो जाती है। सबसे बड़ा खतरा पानी की कमी है — थोड़ा-थोड़ा ORS या साफ पानी पीते रहना सबसे ज़रूरी है। उल्टी रुकने तक ठोस खाना बंद रखें, फिर चावल या टोस्ट से शुरू करें। अगर 24 घंटे से ज़्यादा उल्टी हो या खून आए — डॉक्टर से मिलें।\n\n");
                        break;

                    case "Body Ache":
                        info.append("💪 बदन दर्द\n")
                                .append("बीमारी के दौरान बदन दर्द इसलिए होता है क्योंकि आपकी प्रतिरक्षा प्रणाली साइटोकाइन्स नामक रसायन छोड़ती है जो पूरे शरीर में सूजन पैदा करते हैं — यह असल में इस बात का संकेत है कि शरीर लड़ रहा है। आराम, गर्म पानी से नहाना, पानी पीना, और पैरासिटामोल से राहत मिलती है। बीमारी के बाद भी बदन दर्द जारी रहे या किसी जोड़ में बहुत तेज़ दर्द हो — जांच करवाएं।\n\n");
                        break;

                    case "Dizziness":
                        info.append("💫 चक्कर आना\n")
                                .append("चक्कर अक्सर किसी साधारण कारण से होते हैं — पानी की कमी, जल्दी उठना, कम शुगर, या थकान। बैठ जाएं या लेट जाएं, पानी पिएं, और कुछ हल्का खाएं — कुछ ही मिनटों में ठीक हो जाता है। अगर चक्कर बहुत तेज़ हों, सीने में दर्द हो, बोलने में तकलीफ हो, या संतुलन खो जाए — यह गंभीर हो सकता है, तुरंत मदद लें।\n\n");
                        break;

                    case "Skin Rash":
                        info.append("🔴 त्वचा पर चकत्ते\n")
                                .append("चकत्तों के कई कारण हो सकते हैं — एलर्जी, गर्मी, वायरल इन्फेक्शन, या एक्ज़िमा। ज़्यादातर हल्के चकत्ते कुछ दिनों में ठंडे कपड़े और कैलामाइन लोशन से ठीक हो जाते हैं। खुजलाएं नहीं — इससे त्वचा टूट सकती है और संक्रमण हो सकता है। अगर चकत्ते तेज़ी से फैलें, बुखार के साथ हों, या त्वचा के नीचे छोटे खून के धब्बे दिखें — तुरंत डॉक्टर के पास जाएं।\n\n");
                        break;

                    case "Eye Discomfort":
                        info.append("👁️ आँखों में तकलीफ\n")
                                .append("आँखों की तकलीफ ज़्यादातर लंबे समय तक स्क्रीन देखने से होती है — इसमें सूखापन, जलन, और धुंधला दिखना शामिल है। 20-20-20 का नियम मदद करता है: हर 20 मिनट में 20 फीट दूर 20 सेकंड देखें। आई ड्रॉप्स से सूखापन दूर होता है। अगर आँख लाल हो, पानी या चिपचिपा पदार्थ आए, बहुत दर्द हो, या नज़र बदले — तुरंत डॉक्टर से मिलें।\n\n");
                        break;

                    case "Toothache":
                        info.append("🦷 दांत दर्द\n")
                                .append("दांत दर्द आमतौर पर सड़न, टूटा हुआ दांत, फोड़ा, या मसूड़ों की बीमारी से होता है। गर्म नमक के पानी से कुल्ला सूजन कम करता है, और पैरासिटामोल दर्द को अस्थायी रूप से ठीक कर सकती है — लेकिन ये इलाज नहीं हैं। दांत का संक्रमण बिना इलाज के फैल सकता है, इसलिए 2 दिन के भीतर दंत चिकित्सक के पास जाएं।\n\n");
                        break;

                    case "Chest Pain":
                        info.append("❤️ सीने में दर्द\n")
                                .append("सीने में दर्द हमेशा ध्यान देने योग्य है। यह एसिड रिफ्लक्स, मांसपेशियों में खिंचाव, या चिंता से हो सकता है — लेकिन दिल या फेफड़ों की समस्या भी हो सकती है। दर्द जो दबाने जैसा हो, बाज़ू या जबड़े तक फैले, या पसीने और सांस की तकलीफ के साथ हो — तुरंत 102/108 पर कॉल करें। अगर आप कारण नहीं समझ पा रहे — उसी दिन डॉक्टर से मिलें।\n\n");
                        break;

                    case "Shortness of Breath":
                        info.append("🌬️ सांस लेने में तकलीफ\n")
                                .append("सांस की तकलीफ कई कारणों से हो सकती है — चिंता, अस्थमा से लेकर दिल की समस्या तक। मेहनत के बाद हल्की सांस फूलना सामान्य है, लेकिन आराम के समय सांस फूलना, धीरे-धीरे बढ़ना, या सीने में दर्द के साथ होना — यह मेडिकल इमरजेंसी है। अगर आप अनिश्चित हैं — जांच करवाना हमेशा इंतज़ार करने से बेहतर है।\n\n");
                        break;

                    case "Nausea":
                        info.append("😮 जी मिचलाना\n")
                                .append("जी मिचलाना — उल्टी जैसी असहज अनुभूति — मोशन सिकनेस, खाने, तनाव, दवाओं, या पेट के वायरस से हो सकती है। अदरक की चाय, थोड़ा-थोड़ा हल्का खाना, और खाने के बाद सीधे रहने से मदद मिलती है। धीरे-धीरे पानी पिएं। अगर जी मिचलाना लगातार हो, तेज़ पेट दर्द के साथ हो, या 24 घंटे से ज़्यादा पानी नहीं पी पा रहे — डॉक्टर से मिलें।\n\n");
                        break;

                    case "Weakness":
                        info.append("🔋 कमज़ोरी\n")
                                .append("बीमारी के दौरान कमज़ोरी सामान्य है — शरीर संक्रमण से लड़ने में ऊर्जा लगाता है। एनीमिया, कम शुगर, पानी की कमी, या थायराइड की समस्या भी कारण हो सकते हैं। संतुलित खाना, पानी पीना, और आराम से मदद मिलती है। शरीर के एक तरफ अचानक बहुत तेज़ कमज़ोरी — चेहरे, बाज़ू, या पैर में — तुरंत इमरजेंसी में जाएं, यह स्ट्रोक का संकेत हो सकता है।\n\n");
                        break;

                    case "Weight Loss":
                        info.append("⚖️ वज़न का घटना\n")
                                .append("बिना कोशिश के वज़न कम होना — खासकर कुछ महीनों में 4–5 किलो से ज़्यादा — शरीर का संकेत है। थायराइड की अधिकता, मधुमेह, पुराना संक्रमण, या पाचन संबंधी समस्याएं कारण हो सकती हैं। घबराने की ज़रूरत नहीं, लेकिन जांच ज़रूरी है। ब्लड टेस्ट और डॉक्टर से मिलना जल्दी दिशा दे सकता है।\n\n");
                        break;

                    case "Blood in Stool":
                        info.append("🩸 मल में खून\n")
                                .append("मल में खून चमकीला लाल (निचले पाचन तंत्र से — जैसे बवासीर) या गहरा काला (ऊपरी पेट से रक्तस्राव) हो सकता है। दोनों में डॉक्टरी जांच ज़रूरी है — भले ही आप ठीक महसूस कर रहे हों। कई कारण साधारण और इलाज योग्य होते हैं, लेकिन कुछ को जल्दी जांच की ज़रूरत होती है। खुद दवा न लें।\n\n");
                        break;

                    case "Blood in Urine":
                        info.append("🩺 पेशाब में खून\n")
                                .append("पेशाब में खून — चाहे दिखे या टेस्ट में पता चले — नज़रअंदाज़ न करें। मूत्र मार्ग संक्रमण, पथरी, या मूत्राशय की जलन आम कारण हैं। कभी-कभी किडनी या मूत्राशय की गंभीर समस्या भी हो सकती है। खूब पानी पिएं और उसी दिन डॉक्टर से मिलें। यूरिन टेस्ट और अल्ट्रासाउंड से आमतौर पर कारण पता चल जाता है।\n\n");
                        break;

                    case "Diarrhea":
                        info.append("🚽 दस्त\n")
                                .append("दस्त शरीर का बैक्टीरिया, वायरस, या जलन पैदा करने वाले खाने को जल्दी बाहर निकालने का तरीका है। ज़्यादातर मामले 2 दिनों में ठीक हो जाते हैं — लेकिन सही हाइड्रेशन ज़रूरी है। ORS (ओरल रिहाइड्रेशन सॉल्यूशन) सबसे अच्छा है — सादा पानी अकेला काफी नहीं होता। केला, चावल, टोस्ट खाएं। डेयरी, मसालेदार, और तले खाने से बचें। अगर दस्त में खून हो या 3 दिन से ज़्यादा रहे — डॉक्टर से मिलें।\n\n");
                        break;

                    case "Stomach Ache":
                        info.append("🫃 पेट दर्द\n")
                                .append("पेट दर्द बहुत आम है और इसके कई कारण हो सकते हैं — अपच, गैस, कब्ज़, पेट का वायरस, मासिक दर्द, या तनाव। गर्म सेंक और हर्बल चाय से हल्के दर्द में राहत मिलती है। भारी, मसालेदार, या तला खाना बंद रखें। अगर दर्द बहुत तेज़ हो, अचानक आए, दाईं तरफ नीचे हो (अपेंडिक्स का संकेत), या कुछ घंटों में ठीक न हो — डॉक्टर से मिलें।\n\n");
                        break;
                }
            } else {
                switch (s) {

                    case "Fever":
                        info.append("🌡️ Fever\n")
                                .append("A fever is your body's natural defence mechanism — it raises your temperature to make it harder for viruses and bacteria to survive. A mild fever (up to 38.5°C) is usually safe to manage at home with rest and fluids. If it goes above 39.5°C, lasts more than 3 days, or comes with a stiff neck or rash, see a doctor promptly.\n\n");
                        break;

                    case "Cough":
                        info.append("🫁 Cough\n")
                                .append("A cough is your airway's way of clearing out irritants, mucus, or infection. A dry cough is often caused by viral infections or allergies, while a wet cough with phlegm usually points to a chest infection. Most coughs resolve within 2 weeks. If you're coughing up blood, or it's been going on for more than 3 weeks, get it checked.\n\n");
                        break;

                    case "Headache":
                        info.append("🧠 Headache\n")
                                .append("Headaches are one of the most common symptoms and are usually harmless. Tension headaches feel like a tight band around the head and are often caused by stress, poor posture, or screen time. Dehydration is a surprisingly common trigger — drinking water often helps quickly. Migraines are more intense, often one-sided, and may come with nausea or light sensitivity. A sudden severe headache unlike any before warrants immediate medical attention.\n\n");
                        break;

                    case "Fatigue":
                        info.append("😴 Fatigue\n")
                                .append("Fatigue is more than just feeling tired — it's a persistent lack of energy that doesn't fully go away with rest. It can be caused by poor sleep, anaemia, thyroid issues, viral infections, or even stress and anxiety. Short-term fatigue during an illness is normal. If it's been going on for more than 2 weeks without a clear reason, a simple blood test can often find the cause.\n\n");
                        break;

                    case "Sore Throat":
                        info.append("🗣️ Sore Throat\n")
                                .append("A sore throat is most commonly caused by a viral infection like the common cold, which means antibiotics won't help. It usually gets better on its own within 5–7 days. Warm salt water gargles, honey in warm tea, and staying hydrated can speed up recovery. If your throat is very swollen, you have white patches, or you're struggling to swallow, it could be a bacterial infection — worth seeing a doctor.\n\n");
                        break;

                    case "Vomiting":
                        info.append("🤢 Vomiting\n")
                                .append("Vomiting is usually triggered by a stomach virus, food poisoning, or motion sickness and tends to resolve within 24 hours. The biggest risk with vomiting is dehydration — sipping small amounts of ORS or clear fluids regularly is the most important thing you can do. Avoid solid food until the vomiting settles, then ease back in with bland foods like rice or toast. If vomiting continues beyond 24 hours or there's blood, seek medical attention.\n\n");
                        break;

                    case "Body Ache":
                        info.append("💪 Body Ache\n")
                                .append("Body aches during an illness happen because your immune system releases chemicals called cytokines that cause inflammation throughout the body — this is actually a sign your body is fighting back. Rest, warm baths, staying hydrated, and paracetamol can help ease the discomfort. Body aches that persist long after an illness or that are very severe in specific joints may need further investigation.\n\n");
                        break;

                    case "Dizziness":
                        info.append("💫 Dizziness\n")
                                .append("Dizziness is often caused by something simple — dehydration, getting up too quickly, low blood sugar, or tiredness. Sitting or lying down, drinking water, and having a light snack usually helps within minutes. If dizziness is severe, comes with chest pain, difficulty speaking, or loss of balance, it could signal something more serious and needs immediate attention.\n\n");
                        break;

                    case "Skin Rash":
                        info.append("🔴 Skin Rash\n")
                                .append("Rashes have many causes — allergic reactions, heat, viral infections, or skin conditions like eczema. Most mild rashes are itchy but harmless and resolve within a few days with cool compresses and calamine lotion. Avoid scratching as it can break the skin and cause infection. A rash that spreads rapidly, comes with fever, or looks like tiny blood spots under the skin needs urgent medical evaluation.\n\n");
                        break;

                    case "Eye Discomfort":
                        info.append("👁️ Eye Discomfort\n")
                                .append("Eye discomfort is most commonly caused by digital eye strain from prolonged screen use — symptoms include dryness, burning, and blurred vision. The 20-20-20 rule helps: every 20 minutes, look at something 20 feet away for 20 seconds. Lubricating eye drops can relieve dryness. If the eye is red, producing discharge, very painful, or your vision has changed, see a doctor promptly.\n\n");
                        break;

                    case "Toothache":
                        info.append("🦷 Toothache\n")
                                .append("Toothache is usually caused by tooth decay, a cracked tooth, an abscess, or gum disease. Warm salt water rinses can reduce inflammation, and paracetamol can manage pain temporarily — but these are not cures. A dental infection left untreated can spread, so it's important to see a dentist within a couple of days rather than hoping it goes away.\n\n");
                        break;

                    case "Chest Pain":
                        info.append("❤️ Chest Pain\n")
                                .append("Chest pain always deserves attention. While it can be caused by something benign like acid reflux, muscle strain, or anxiety, it can also signal heart or lung problems. Pain that is crushing, spreading to the arm or jaw, or comes with sweating and breathlessness needs emergency attention — call 102/108 immediately. Even if you suspect it's minor, chest pain you can't explain should be evaluated by a doctor the same day.\n\n");
                        break;

                    case "Shortness of Breath":
                        info.append("🌬️ Shortness of Breath\n")
                                .append("Feeling short of breath can be caused by a range of things — from anxiety and asthma to more serious conditions like heart failure. Mild breathlessness after exertion is normal, but breathlessness at rest, worsening over time, or accompanied by chest pain or bluish lips is a medical emergency. If you're unsure, it's always safer to get checked than to wait.\n\n");
                        break;

                    case "Nausea":
                        info.append("😮 Nausea\n")
                                .append("Nausea is that uncomfortable urge to vomit — it can come from motion sickness, food, stress, medication, or a stomach bug. Ginger tea, small bland meals, and staying upright after eating can help. Sipping fluids slowly is better than gulping. If nausea is persistent, comes with severe abdominal pain, or you can't keep any fluids down for more than 24 hours, see a doctor.\n\n");
                        break;

                    case "Weakness":
                        info.append("🔋 Weakness\n")
                                .append("A general feeling of weakness is common during illness as your body diverts energy to fighting infection. It can also be caused by anaemia, low blood sugar, dehydration, or thyroid problems. Eating a balanced meal, staying hydrated, and resting usually helps. Sudden severe weakness in one side of the body, face, or limbs needs emergency attention as it can be a sign of stroke.\n\n");
                        break;

                    case "Weight Loss":
                        info.append("⚖️ Unexplained Weight Loss\n")
                                .append("Losing weight without trying — especially more than 4–5 kg over a few months — is something your body is trying to tell you. It can be caused by thyroid overactivity, diabetes, chronic infection, or digestive conditions. It's rarely something to panic about, but it always warrants investigation. A blood test and a visit to your doctor can usually point in the right direction quickly.\n\n");
                        break;

                    case "Blood in Stool":
                        info.append("🩸 Blood in Stool\n")
                                .append("Blood in the stool can appear bright red (usually from the lower digestive tract — like haemorrhoids) or dark and tarry (indicating bleeding higher up in the gut). Either way, it should always be evaluated by a doctor — even if you feel fine otherwise. Many causes are benign and treatable, but some require prompt investigation. Don't ignore it or self-medicate.\n\n");
                        break;

                    case "Blood in Urine":
                        info.append("🩺 Blood in Urine\n")
                                .append("Blood in urine — whether you can see it or it's detected on a test — is not something to dismiss. Common causes include urinary tract infections, kidney stones, or bladder irritation. Drink plenty of water and see a doctor the same day. You'll usually need a urine test and possibly an ultrasound to find the cause.\n\n");
                        break;

                    case "Diarrhea":
                        info.append("🚽 Diarrhea\n")
                                .append("Diarrhea is the body's rapid way of expelling irritants. Most cases resolve within 2 days with proper hydration. ORS is the most effective way to replace lost fluids and salts — plain water alone isn't enough. Stick to bland foods: bananas, rice, toast. Avoid dairy, fatty, and spicy food. If diarrhea contains blood, or lasts beyond 3 days, see a doctor.\n\n");
                        break;

                    case "Stomach Ache":
                        info.append("🫃 Stomach Ache\n")
                                .append("Stomach pain is extremely common — indigestion, gas, constipation, a stomach bug, period cramps, or stress can all cause it. A warm compress and herbal tea can help with mild cramps. Avoid heavy, spicy, or oily food until it settles. Pain that is severe, comes on suddenly, is focused in the lower right (appendix area), or doesn't improve within a few hours should be seen by a doctor.\n\n");
                        break;
                }
            }
        }

        if (info.length() == 0) {
            info.append(isHindi
                    ? "अपने लक्षणों पर नज़र रखें और अगर वे बने रहें तो डॉक्टर से मिलें।"
                    : "Monitor your symptoms and consult a healthcare professional if they persist.");
        }

        return info.toString();
    }
}